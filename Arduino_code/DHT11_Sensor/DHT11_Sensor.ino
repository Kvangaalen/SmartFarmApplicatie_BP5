#include "DHT.h"

#define DHTPIN 4        // Digital pin verbonden met DHT11 uitgang
#define DHTTYPE DHT11   // DHT 11

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  Serial.println(F("DHT11 Concept test."));
  dht.begin();
}

void loop() {
  delay(2000);  //Wacht 2 sec
  float Lucht_Vochtigheid = dht.readHumidity();
  float Temperatuur = dht.readTemperature(); //Temeperatuur in graden Celsius

  // Controleer de integriteit van de sensor meetdata
  if (isnan(Lucht_Vochtigheid) || isnan(Temperatuur)) {
    Serial.println(F("Fout tijdens uitlezen van de DHT sensor!"));
    return;
  }

  Serial.print(F(" Lucht vochtigheid: "));
  Serial.print(Lucht_Vochtigheid);
  Serial.print(F("%  Temperatuur: "));
  Serial.println(Temperatuur);

}
