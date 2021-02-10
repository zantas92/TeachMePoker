# TeachMePoker

TeachMePoker med JDK 15 & Java-FX 15

Innan ni börjar: Se till att ni hämtat senaste versionen av TeachMePoker från GitHub!

Om ni ej har JDK 15, ladda ner det här (för att se om ni redan har detta, följ steg 1 i instruktionerna nedan och se om den redan finns i listan): https://www.oracle.com/se/java/technologies/javase-downloads.html 

Om ni ej har Java-FX 15, ladda ner det här:
https://gluonhq.com/products/javafx/ OBS! Ta ej Java-FX 11 under Long Term Support, välj Java-FX 15 under Latest Release längre ner. Välj JavaFX SDK för ditt operativsystem.

Lägga till senaste versionen av JDK och Java-FX i projektet:
Gå in på File → Project structure och i menyvalet Project Settings →  Project kan du ändra Project SDK till 15.
Välj sedan Libraries under Project Settings i samma meny till vänster, och tryck sedan på + och Java för att lägga till ett nytt Java-bibliotek. Leta fram din mappen där du har lagt ditt Java-FX och markera undermappen som heter “lib” och lägg till den.

Högerklicka på Main-klassen och välj “Edit Main.main()” eller “More Run/Debug → Modify Run Configuration” om det finns. Annars kan man köra main en gång (vilket kommer att generera fel) och sedan klicka på “Main” bredvid den gröna pilen för att köra programmet högst upp till höger, och välja “Edit Configurations”.
 
Om fältet VM-options ej finns, tryck på “Modify Options” och lägg till den. I fältet VM-options, lägg till följande: --upgrade-module-path "Din\Path\Till\JavaFX\lib". Det vill säga er egen path till er Java-FX “lib”-mapp är vad som fylls i inom citationstecken.



