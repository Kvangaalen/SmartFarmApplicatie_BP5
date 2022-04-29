#include "DHT.h"

#define DHTPIN 4        // Digital pin verbonden met DHT11 uitgang
#define DHTTYPE DHT11   // DHT 11

//Variabelen voor Cap Sensor
const int dry = 1000; // waarde bij droge sensor
const int wet = 100;  // waarde bij sensor in water

int sensorVal;
int percentageHumididy;

//Variabelen voor DHT11 lucht vochtigheid sensor
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  Serial.println("DHT11 lucht vochtigheid sensor + Capacitieve bodem sensor.");
  dht.begin();
}

void loop() {

  // *** Code voor DHT11 sensor ***
  delay(2000);  //Wacht 2 sec
  float Lucht_Vochtigheid = dht.readHumidity();
  float Temperatuur = dht.readTemperature(); //Temeperatuur in graden Celsius

  // Controleer de integriteit van de sensor meetdata
  if (isnan(Lucht_Vochtigheid) || isnan(Temperatuur)) {
    Serial.println(F("Fout tijdens uitlezen van de DHT sensor!"));
    return;
  }

  Serial.print("Lucht vochtigheid: ");
  Serial.print(Lucht_Vochtigheid);
  Serial.print("%  Temperatuur: ");
  Serial.print(Temperatuur);

  // *** Code voor Capacitieve bodem sensor ***
  sensorVal = analogRead(A0);
  percentageHumididy = map(sensorVal, wet, dry, 100, 0); 
  Serial.print(" | Grond vochtigheid : ");
  Serial.print(percentageHumididy);
  Serial.println(" %");
}
