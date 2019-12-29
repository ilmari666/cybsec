LINK:
-----
https://github.com/ilmari666/cybsec
Based on the Springboot-template as per course material that can be installed and run with suitably configured Netbeans and Maven.
Five flaws as per https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf

This document can be read at https://github.com/ilmari666/cybsec/blob/master/README.md

FLAW 1:
-------
# A2:2017 Broken Authentication

There is no quality check for passwords. In fact the software even comes with a built-in account with credentials of admin / admin.

If there are default passwords either built into the software or passwords preset for users by administrators it should be required that these are changed at first use. If accounts are not used after a short set period these accounts with default passwords should be deactivated / deleted and manually reactivated by administrators if necessary.

The software should implement basic checks for passwords.
Allowed passwords should not be obvious ie. not the same as the login and not feature in a list of popular passwords.
Usually this can be achieved by simple password rules that require the password to have lowercase and capital alphabets, numbers as well as symbols avoiding repetition.  This makes it pretty much impossible for a human to figure out the password in use but in the era of powerful computation the length of the password is of more importance and if you have complex hard to remember passwords you easily end up with people recycling the same credentials in multiple sites or post-it notes around the computer with the passwords in plain form.  There's only a few "gFs_^6ar#! -type passwords a user can possible remember and change regularly to new ones.
So perhaps the complexity regarding passwords should not be as strict as "gFs_^6ar#! is hard to guess but Kouvol4nKauha-autot is probably a much better password.


FLAW 2:
-------
# A5:2017 Broken Access Control

The list view of all signups provides a view/edit link to user administrated signups. The id of the signup is provided in the URL and changing the id to another existing one reveals the same view to another persons data. This is true for both the view form as well as the submit form handler.

In this case some security-by-obscurity could be achieved by hashing ids as incremental ids just beg to be fiddled around with.
This doesn't fix the access control though. The single signup view should check if the viewer is indeed the same as the owner and not only rely on the fact it's not publicly listed for others.
After this is done there's still a CSRF-risk that can be mitigated by enabling CSRF-tokens in the security configuration.

FLAW 3:
-------
# A3:2017 Sensitive Data Exposure

HTTP is vulnerable for eaves-dropping and the social security number required for registeration is transferred as plaintext.
The user password is saved as plaintext. This was often the case in early days of internet as it allowed for easy password retrieval but is terrible for security. 

Hashing and saving the hashed password is the solution for the password. It's also quite popular these days to use a third party authorization service leaving experts handle the sensitive data.
HTTPS needs to be enabled and it can be questioned if it's necessary to display the complete security number. Often in e-commerce you only are shown last four digits and in this case it would probably suffice to show the first six digits.

FLAW 4:
-------
# A6:2017 Security Misconfiguration

The project is a combination of bad programming practices and misconfiguration.
It has default accounts and credentials and framwork provided security features turned off.
CSRF-tokens off, using a non-hashing password engine, no https, no systematic approach for access control except for the need to be logged in.

In the wild this is often the result of using the same security configuration for both development and production which leads to developement 'features' leaking out to production. Thus there should be separate configurations controlled by system variables and build pipeline to differentiate from these two sets and also having as much security features found in production enabled in the development configuration as possible.

FLAW 5:
-------
# A9:2017 Using Components with Known Vulnerabilities

While the project template template originally didn't probably have this issue, time has run it's course and the dependencies it uses now have known vulnerabilities. Running dependency-check-maven reveals the following vulnerable depencencies:

```
logback-core-1.1.7.jar (pkg:maven/ch.qos.logback/logback-core@1.1.7, cpe:2.3:a:logback:logback:1.1.7:*:*:*:*:*:*:*) : CVE-2017-5929
tomcat-embed-core-8.5.6.jar (pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@8.5.6, cpe:2.3:a:apache:tomcat:8.5.6:*:*:*:*:*:*:*, cpe:2.3:a:apache_software_foundation:tomcat:8.5.6:*:*:*:*:*:*:*, cpe:2.3:a:apache_tomcat:apache_tomcat:8.5.6:*:*:*:*:*:*:*) : CVE-2016-6816, CVE-2016-6817, CVE-2016-8735, CVE-2016-8745, CVE-2017-12617, CVE-2017-5647, CVE-2017-5648, CVE-2017-5650, CVE-2017-5651, CVE-2017-5664, CVE-2017-7674, CVE-2017-7675, CVE-2018-11784, CVE-2018-1304, CVE-2018-1305, CVE-2018-1336, CVE-2018-8014, CVE-2018-8034, CVE-2018-8037, CVE-2019-0199, CVE-2019-0221, CVE-2019-0232, CVE-2019-10072
hibernate-validator-5.2.4.Final.jar (pkg:maven/org.hibernate/hibernate-validator@5.2.4.Final, cpe:2.3:a:hibernate:hibernate-validator:5.2.4:*:*:*:*:*:*:*, cpe:2.3:a:redhat:hibernate_validator:5.2.4:*:*:*:*:*:*:*) : CVE-2017-7536
jackson-databind-2.8.4.jar (pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.8.4, cpe:2.3:a:fasterxml:jackson:2.8.4:*:*:*:*:*:*:*, cpe:2.3:a:fasterxml:jackson-databind:2.8.4:*:*:*:*:*:*:*) : CVE-2017-15095, CVE-2017-17485, CVE-2017-7525, CVE-2018-1000873, CVE-2018-11307, CVE-2018-12022, CVE-2018-12023, CVE-2018-14718, CVE-2018-14719, CVE-2018-14720, CVE-2018-14721, CVE-2018-19360, CVE-2018-19361, CVE-2018-19362, CVE-2018-5968, CVE-2018-7489, CVE-2019-12086, CVE-2019-12384, CVE-2019-12814, CVE-2019-14379, CVE-2019-14439, CVE-2019-14540, CVE-2019-16335, CVE-2019-16942, CVE-2019-16943, CVE-2019-17267, CVE-2019-17531
spring-webmvc-4.3.4.RELEASE.jar (pkg:maven/org.springframework/spring-webmvc@4.3.4.RELEASE, cpe:2.3:a:pivotal_software:spring_framework:4.3.4.release:*:*:*:*:*:*:*, cpe:2.3:a:springsource:spring_framework:4.3.4.release:*:*:*:*:*:*:*, cpe:2.3:a:vmware:springsource_spring_framework:4.3.4:*:*:*:*:*:*:*) : CVE-2016-9878, CVE-2018-11039, CVE-2018-11040, CVE-2018-1199, CVE-2018-1257, CVE-2018-1270, CVE-2018-1271, CVE-2018-1272, CVE-2018-1275, CVE-2018-15756
ognl-3.0.8.jar (pkg:maven/ognl/ognl@3.0.8, cpe:2.3:a:ognl_project:ognl:3.0.8:*:*:*:*:*:*:*) : CVE-2016-3093
groovy-2.4.7.jar (pkg:maven/org.codehaus.groovy/groovy@2.4.7, cpe:2.3:a:apache:groovy:2.4.7:*:*:*:*:*:*:*) : CVE-2016-6814
dom4j-1.6.1.jar (pkg:maven/dom4j/dom4j@1.6.1, cpe:2.3:a:dom4j_project:dom4j:1.6.1:*:*:*:*:*:*:*) : CVE-2018-1000632
spring-data-commons-1.12.5.RELEASE.jar (pkg:maven/org.springframework.data/spring-data-commons@1.12.5.RELEASE, cpe:2.3:a:pivotal_software:spring_data_commons:1.12.5.release:*:*:*:*:*:*:*) : CVE-2018-1273
spring-security-web-4.1.3.RELEASE.jar (pkg:maven/org.springframework.security/spring-security-web@4.1.3.RELEASE, cpe:2.3:a:pivotal_software:spring_security:4.1.3.release:*:*:*:*:*:*:*) : CVE-2016-9879, CVE-2018-1199, CVE-2018-1258
spring-core-4.3.4.RELEASE.jar (pkg:maven/org.springframework/spring-core@4.3.4.RELEASE, cpe:2.3:a:pivotal_software:spring_framework:4.3.4.release:*:*:*:*:*:*:*, cpe:2.3:a:springsource:spring_framework:4.3.4.release:*:*:*:*:*:*:*, cpe:2.3:a:vmware:springsource_spring_framework:4.3.4:*:*:*:*:*:*:*) : CVE-2018-11039, CVE-2018-11040, CVE-2018-1199, CVE-2018-1257, CVE-2018-1270, CVE-2018-1271, CVE-2018-1272, CVE-2018-1275, CVE-2018-15756
spring-security-test-4.1.3.RELEASE.jar (pkg:maven/org.springframework.security/spring-security-test@4.1.3.RELEASE, cpe:2.3:a:pivotal_software:spring_security:4.1.3.release:*:*:*:*:*:*:*) : CVE-2018-1199, CVE-2018-1258
spring-security-core-4.1.3.RELEASE.jar (pkg:maven/org.springframework.security/spring-security-core@4.1.3.RELEASE, cpe:2.3:a:pivotal_software:spring_security:4.1.3.release:*:*:*:*:*:*:*) : BREACH attack possible in CSRF tokens, CVE-2016-9879, CVE-2018-1199, CVE-2018-1258
spring-boot-1.4.2.RELEASE.jar (pkg:maven/org.springframework.boot/spring-boot@1.4.2.RELEASE, cpe:2.3:a:pivotal_software:spring_boot:1.4.2.release:*:*:*:*:*:*:*) : CVE-2017-8046, CVE-2018-1196
```

To address this one should always use up-to-date components when starting a new project. A good practice is to incorporate a build-time check for known dependencies utilizing something like dependency-check-maven from OWASP. Third party version control services can also deliver this out of box.

Following newsgroups on your software stack can help you find the most critical issues that need immediate attention.
When a vulnerable dependency is found an updated version without the flaw must be installed or another way of delivering the functionality must be considered. In some cases it's possible to disable the features that use the dependency while a solution is being found.


FLAW Bonus:
-----------
# A10:2017 Insufficient Logging & Monitoring

The application has no logging enabled. This means that potential and successful breaches are not possible to find.

Since storage space is commonly not an issue these days there'x no excuse for not having logging in place. This itself is insufficient though as heuristics analysing the logs must be in place to automatic alarms. In a perfect scenario the logs are structured so that it's possible to manually inspect the logs every now and then to possible find issues that the automation does not flag.



