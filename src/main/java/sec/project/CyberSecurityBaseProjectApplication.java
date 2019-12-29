package sec.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*

tehty:
  A2:2017-Broken Authentication
  -> permits (and utilises) default admin/admin password


  A5:2017-Broken Access Control
  1. toisen postin näkeminen ilman oikeuksien validointia päättelemällä ilmoittautumisen id:n
  2. toisen postin editointi ilman oikeuksien validointia controllerissa
  3. - jos tämän fiksaa validoinnilla silti csrf riski

  A10:2017-Insufficient Logging & Monitoring
    insufficient logging -> https://www.baeldung.com/spring-boot-changing-log-level-at-runtime

  A3:2017-Sensitive Data Exposure
  no-op password encoder -> done fix as bcrypt

  A6:2017 Security Misconfiguration
  -default security features such a csrf tokens are not enabled

  +
*/
/*

  toisen postin editointi ilman validointia controllerissa (näytä vain käyttäjän omat editoitavaksi mutta postissa anna muuttaa kaikkia)
  xss validoimaton kenttä
  sql injektio
  salaisen tiedon paljastus (esimerkiksi json rajapinta voisi palauttaa kaikki tiedot mutta parsinta näyttää vaan tarvittavat

admin/admin default user, voidaan käsitellä vuodettuna tietona jos top 10000 passulistassa?
  päivittämätön vulni depsu

  no-op password encoder -> done fix as bcrypt



*/
/*

eka pläni

https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf

tehdään event kirjautuminen missä 5 top 10 haavoittuvuutta

-voi nähdä toisen nimissä olevaa entryä jos arvaa id:n
-xss nimeksi
-csrf validointi pois, voi editoida toisen entryä
-käyttää depsuaversiota jossa tunnettu haavoittuvuus
-injektio suoralla escapeamattomalla form->kanta jutulla


*/
        


@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
}
