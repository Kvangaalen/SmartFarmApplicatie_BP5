#include <lmic.h>
#include <hal/hal.h>
#include <SPI.h>
#include "DHT.h"
#include <Adafruit_Sensor.h>
#include "Adafruit_TSL2591.h" // TSL2591 LDR-sensor
#define DHTPIN 4        // Digital pin verbonden met DHT11 uitgang
#define DHTTYPE DHT11   // DHT 11

uint8_t mydata[6];
const int dry = 1000; // waarde bij droge sensor
const int wet = 100;  // waarde bij sensor in water
Adafruit_TSL2591 tsl = Adafruit_TSL2591(2591); //= Licht sensor Remco
float lichtSterkte; // variable voor licht
int sensorVal; // 
int Grond_Vochtigheid; // variable voor licht

//Variabelen voor DHT11 lucht vochtigheid sensor
DHT dht(DHTPIN, DHTTYPE);

// AppEUI = 00 59 AC 00 00 01 0A F1
static const u1_t PROGMEM APPEUI[8]={ 0xF1, 0x0A, 0x01, 0x00, 0x00, 0xAC, 0x59, 0x00 };
void os_getArtEui (u1_t* buf) { memcpy_P(buf, APPEUI, 8);}

// DevEUI = 00 59 AC 00 00 1B 0E 8E
static const u1_t PROGMEM DEVEUI[8]={ 0x8E, 0x0E, 0x1B, 0x00, 0x00, 0xAC, 0x59, 0x00 };
void os_getDevEui (u1_t* buf) { memcpy_P(buf, DEVEUI, 8);}

// APPKEY = 3e 91 c5 ed 6c a2 8b 15 07 0b 73 f4 0d 34 30 c5
static const u1_t PROGMEM APPKEY[16] = { 0x3E, 0x91, 0xC5, 0xED, 0x6C, 0xA2, 0x8B, 0x15, 0x07, 0x0B, 0x73, 0xF4, 0x0D, 0x34, 0x30, 0xC5 };
void os_getDevKey (u1_t* buf) { memcpy_P(buf, APPKEY, 16);}

static osjob_t sendjob;
// Schedule TX every this many seconds (might become longer due to duty
// cycle limitations).
const unsigned TX_INTERVAL = 3600; // 1 uur interval
// Pin mapping t.b.v Dragino
const lmic_pinmap lmic_pins = {
 .nss = 10,
 .rxtx = LMIC_UNUSED_PIN,
 .rst = 9,
 .dio = {2, 6, 7},
};

void onEvent (ev_t ev) {
 Serial.print(os_getTime());
 Serial.print(": ");
 switch(ev) {
 case EV_SCAN_TIMEOUT:
 Serial.println(F("EV_SCAN_TIMEOUT"));
 break;
 case EV_BEACON_FOUND:
 Serial.println(F("EV_BEACON_FOUND")); 
 break;
 case EV_BEACON_MISSED:
 Serial.println(F("EV_BEACON_MISSED"));
 break;
 case EV_BEACON_TRACKED:
 Serial.println(F("EV_BEACON_TRACKED"));
 break;
 case EV_JOINING:
 Serial.println(F("EV_JOINING"));
 break;
 case EV_JOINED:
 Serial.println(F("EV_JOINED"));
 // Disable link check validation (automatically enabled
 // during join, but not supported by TTN at this time).
 LMIC_setLinkCheckMode(0);
 break;
 case EV_RFU1:
 Serial.println(F("EV_RFU1"));
 break;
 case EV_JOIN_FAILED:
 Serial.println(F("EV_JOIN_FAILED"));
 break;
 case EV_REJOIN_FAILED:
 Serial.println(F("EV_REJOIN_FAILED"));
 break;
 break;
 case EV_TXCOMPLETE:
 Serial.println(F("EV_TXCOMPLETE (includes waiting for RX windows)"));
 if (LMIC.txrxFlags & TXRX_ACK)
 Serial.println(F("Received ack"));
 if (LMIC.dataLen) {
 Serial.print(F("Received "));
 Serial.print(LMIC.dataLen);

 Serial.println(F(" bytes of payload"));
 }
 // Schedule next transmission
 os_setTimedCallback(&sendjob, os_getTime()+sec2osticks(TX_INTERVAL), do_send);
 break;
 case EV_LOST_TSYNC:
 Serial.println(F("EV_LOST_TSYNC"));
 break;
 case EV_RESET:
 Serial.println(F("EV_RESET"));
 break;
 case EV_RXCOMPLETE:
 // data received in ping slot
 Serial.println(F("EV_RXCOMPLETE"));
 break;
 case EV_LINK_DEAD:
 Serial.println(F("EV_LINK_DEAD"));
 break;
 case EV_LINK_ALIVE:
 Serial.println(F("EV_LINK_ALIVE"));
 break;
 default:
 Serial.println(F("Unknown event"));
 break;
 }
}
void do_send(osjob_t* j){
 // Controleer of er geen lopende TX/RX-job is
 if (LMIC.opmode & OP_TXRXPEND) {
 Serial.println(F("OP_TXRXPEND, not sending"));
 } else {

  // ---------------------------------------
  //      Licht TSL2591 sensor Remco
  uint32_t lum = tsl.getFullLuminosity();
  uint16_t ir, full;
  ir = lum >> 16;
  full = lum & 0xFFFF;
  int lichtSterkte = tsl.calculateLux(full, ir);

  //-----------------------------------------
  // Tempratuur & luchtvochtigheid DHT11 sensor Koen
  float Lucht_Vochtigheid = dht.readHumidity();
  float Temperatuur = dht.readTemperature(); //Temeperatuur in graden Celsius
  // Bodemsensor Capacitieve Sensor Koen
  sensorVal = analogRead(A0);
  Grond_Vochtigheid = map(sensorVal, wet, dry, 100, 0); 
  
  Serial.print("Lucht vochtigheid ; ");
  Serial.print(Lucht_Vochtigheid);
  Serial.print(" ; Temperatuur ; ");
  Serial.print(Temperatuur);
  Serial.print("; Grond vochtigheid ; ");
  Serial.print(Grond_Vochtigheid);
  Serial.print("; Lichtsterkte ; ");
  Serial.println (lichtSterkte);

  // rond data af op een heel getal

  mydata[0] = round(Lucht_Vochtigheid); 
  

  mydata[1] = round(Grond_Vochtigheid);
  

  mydata[2] = round(Temperatuur);


  // High- low-Byte Remco
  int variable = lichtSterkte;
  byte high_byte = highByte(variable);
  byte low_byte = lowByte(variable);

  mydata[3] = round(high_byte);
  mydata[4] = round(low_byte);

 LMIC_setTxData2(1, mydata, sizeof(mydata)-1, 0);
 Serial.println(F("Packet klaar voor verzending."));
 }
 // Volgende TX is gepland na TX_COMPLETE event.
}

void setup() {
 // starten van dht sensor (luchtvochtigheid & tempratuur) 
 dht.begin();
 // starten van TSL2591 (lichtsensor)
 tsl.begin();
 Serial.begin(9600);
 Serial.println(F("Starting"));
 #ifdef VCC_ENABLE
 // For Pinoccio Scout boards
 pinMode(VCC_ENABLE, OUTPUT);
 digitalWrite(VCC_ENABLE, HIGH);
 delay(1000);
 #endif
 // LMIC init
 os_init();
 // Reset de MAC-status. Sessie en hangende gegevensoverdrachten worden verwijderd.
 LMIC_reset();
 // Start opdracht (verzenden start automatisch ook OTAA)
 do_send(&sendjob);
}
void loop() {
 os_runloop_once();
 
} 
