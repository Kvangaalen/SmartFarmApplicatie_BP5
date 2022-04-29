
const int dry = 1000; // waarde bij droge sensor
const int wet = 100; // waarde bij sensor in water

int sensorVal;
int percentageHumididy;


void setup()
{ 
   Serial.begin(9600);
}

void loop()
{
   sensorVal = analogRead(A0);
 
   percentageHumididy = map(sensorVal, wet, dry, 100, 0); 
   
   Serial.print("Grond vochtigheid : ");
   Serial.print(percentageHumididy);
   Serial.println(" %");
   
   delay(1000);
}
