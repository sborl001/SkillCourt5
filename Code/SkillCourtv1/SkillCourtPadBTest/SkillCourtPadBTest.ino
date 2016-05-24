#include <SoftwareSerial.h>
#include <Adafruit_NeoPixel.h>

// pins for the LEDs:
//const int redPin = 8;
//const int greenPin = 9;
//const int bluePin = 10;
//#define PIN 6

//bluetooth
//int bluetoothTx = 2;
//int bluetoothRx = 3;

SoftwareSerial bluetooth(2, 3);

boolean conn = false;
boolean routine = false;

Adafruit_NeoPixel * strip = new Adafruit_NeoPixel(150); //NEO_GRB + NEO_KHZ800
//Adafruit_NeoPixel strip = Adafruit_NeoPixel(150);

void setup()
{ 
   // make the pins outputs:
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);

  //Initialize NeoPixel Strip
  strip->begin();
  //strip.begin();
  //strip.setBrightness(254);

  // set initial values  0,0,0
  setLed(0,0,254);
  setLed2(0,0,255);

  /*for(int x = 0; x < 3; x++)
  {
    setLed2(0,0,0);
  for(int i = 0; i < 30; i += 3)
  {
    strip.setPixelColor(i, 255, 0, 0);
    strip.show();
    delay(60);
  }

  for(int i = 1; i < 30; i += 3)
  {
    strip.setPixelColor(i, 255, 255, 255);
    strip.show();
    delay(60);
  }

  for(int i = 2; i < 30; i += 3)
  {
    strip.setPixelColor(i, 0, 0, 255);
    strip.show();
    delay(60);
  }
  }

  //strip.show();*/
  
  //9600
  Serial.begin(9600);
  //sendVoltage();
  establishContact();
  
  
  bluetooth.begin(115200); //115200
  bluetooth.print(F("$$$"));
  delay(100);
  bluetooth.println(F("U,9600,N")); //9600
  bluetooth.begin(9600); //9600
  
  
}

void loop()
{ 
  String recv;
  
  if( !conn) 
  {
    //254,254,254
    setLed(254, 254, 254);
    //setLed2(254, 254, 254);
    if(bluetooth.available())
    { 
      recv =  bluetooth.readStringUntil('\n');
      if(recv == "Hello")
      {
        
        delay(10);
        bluetooth.print(F("Hello from pad\n"));
        conn = true;
      }
    }    
  }
  else
  {
    if(!routine)//it is already connected to the Android device but routine hasn't been received
    {
      //100,159,30
      setLed(100, 159, 30);
      //setLed2(100, 159, 30); //NeoPixel
      if(bluetooth.available())
      {
        
        recv =  bluetooth.readStringUntil('\n');
        
        if(recv.length() == 13)//else we can check by sendin a message back
        {
          Serial.print(recv);
          routine = true;
        }
        else if(recv == "disc")
        {
          setLed(0, 0, 0);
          conn = false;
        }
      }
    }
    else//routine playing wating to know which light to turn on or if the end to send back stats and reset variables
    {
      
      if(Serial.available())
      {
        
          recv = Serial.readStringUntil('\n');
          
          
          if(recv == "r")
          {
            setLed(254, 0, 0);
            setLed2(254, 0, 0); //NeoPixel
            
            delay(1000);
            sendVoltage();
          }
          else if(recv == "g")
          {
            setLed(0, 254, 0);
            setLed2(0, 254, 0); //NeoPixel
            
            delay(1000);
            sendVoltage();
          }
          else if(recv == "b")
          {
            setLed(0, 0, 254);
            setLed2(0, 0, 254);
          }
          else if(recv == "o") //recv == "o"
          {
            setLed(0, 0, 0);
            setLed2(0, 0, 0);
            
          }
          else if(recv[0] == 'p')//routine is over
          {
            setLed(100, 100, 100); //was commented
            setLed2(100, 100, 100);

            //delete[] strip;
            //strip.updateLength(80);
            //strip->updateLength(80);

            delay(500);
            
            bluetooth.print(recv);//the problem is here for some reason the message is not being received
            
            //Serial.print(recv);
            delay(500);

            //strip = new Adafruit_NeoPixel(150);
            //setLed2(100, 100, 100);
            //strip.updateLength(150);
            //strip->updateLength(150);
            routine = false;
          }      
      }
    }
  }
}

void establishContact() {
  
  while (Serial.available() <= 0) {
    /*for (int i = 0;i < 1000; i++){
    Serial.print('B');
    }*/
    Serial.print('B');   // send a capital A  
  }
  
}

void sendVoltage() {
  
  while (Serial.available() <= 0) {
    // Read the input on analog pin 0:
    int sensorValue = analogRead(A0);

    // Convert the analog reading (which goes from 0 - 1023) to a voltage (0 - 5V):
    int voltage = sensorValue * (5.0 / 1023.0); //was float
  
    // Print out the value you read:
    //Serial.print(voltage);
    //delay(100);

    if(voltage >= 1)
    {
      Serial.print(voltage);
      break;
    }   
  }
}

void setLed(int redVal, int greenVal, int blueVal)
{
     analogWrite(8, redVal); //analogWrite
     analogWrite(9, greenVal);
     analogWrite(10, blueVal);
}

void setLed2(int redVal, int greenVal, int blueVal)
{ 
  int i = 0;
  
     for(i = 0; i < 150; i++)
     {
      strip->setPixelColor(i, redVal, greenVal, blueVal);
      //strip.setPixelColor(i, redVal, greenVal, blueVal);
      
      //strip.show();
      //delay(100);
     }
     
     strip->show();
     //strip.show();
}
