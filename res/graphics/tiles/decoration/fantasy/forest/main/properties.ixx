ixxFile {
     version = 4;
     name = "grass main properties";
 }

 default {
     name = "main";
 }

 alternatives {
     length = 5;
     0 {
         name = "main2";
         chance = 0.20;
     }
     1 {
         name = "main3";
         chance = 0.05;
     }
     2 {
         name = "main4";
         chance = 0.20;
     }
     3 {
         name = "main5";
         chance = 0.20;
     }
     4 {
         name = "main6";
         chance = 0.07;
     }
 }