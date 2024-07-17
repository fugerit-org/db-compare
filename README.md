# db-compare

Simple helper to handle update / insert / delete in a SAFE mode.

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](https://github.com/fugerit-org/db-compare/blob/master/CHANGELOG.md) 
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/db-compare.svg)](https://mvnrepository.com/artifact/org.fugerit.java/db-compare)
[![license](https://img.shields.io/badge/License-Apache%20License%202.0-teal.svg)](https://opensource.org/licenses/Apache-2.0)
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_db-compare&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_db-compare)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_db-compare&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_db-compare)

[![Java runtime version](https://img.shields.io/badge/run%20on-java%2011+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Java build version](https://img.shields.io/badge/build%20on-java%2011+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)

## Quickstart

Add maven dependency :

```xml
<dependency>
    <groupId>org.fugerit.java</groupId>
    <artifactId>db-compare</artifactId>
    <version>${db-compare-version}</version>
</dependency>	
```

Sample usage : 

```java
        try ( InputStream configIs = ...;
              ConnectionFactoryCloseable cf1 = ...;
              ConnectionFactoryCloseable cf2 = .. ) {
            DBCompareConfig config = this.facade.readConfig( configIs );
            String schema = "PUBLIC";
            DBCompareOutput output = this.facade.compare( config, cf1, schema, cf2, schema );
            // print include equals
            log.info( "\nDiff (include equals)" );
            DBCompareUtils.printDiff( output, true );
            // print exclude equals
            log.info( "\nDiff (exclude equals)" );
            DBCompareUtils.printDiff( output, false );
        }
```

