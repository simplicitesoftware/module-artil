<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
* * *

`Artil` module definition
=========================

TODO : Clarify Build jar process
====================

```bash
wget http://archive.apache.org/dist/lucene/java/8.0.0/lucene-8.0.0.tgz
tar -xvf lucene-8.0.0.tgz && rm -f lucene-8.0.0.tgz
#cp lucene-8.0.0/queryparser/lucene-queryparser-8.0.0.jar tomcat/webapps/ROOT/WEB-INF/lib/
cp lucene-8.0.0/analysis/common/lucene-analyzers-common-8.0.0.jar tomcat/webapps/ROOT/WEB-INF/lib/
#cp lucene-8.0.0/codecs/lucene-codecs-8.0.0.jar tomcat/webapps/ROOT/WEB-INF/lib/
#cp lucene-8.0.0/backward-codecs/lucene-backward-codecs-8.0.0.jar tomcat/webapps/ROOT/WEB-INF/lib/
rm -rf lucene-8.0.0

git clone https://github.com/dermotte/LIRE.git
cd LIRE
gradle jar
cd ..
cp LIRE/build/libs/LIRE-1.0_b05.jar tomcat/webapps/ROOT/WEB-INF/lib/
rm -rf LIRE/

sim tomcat-stop
sim tomcat-star
```

`ArtArtist` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artArtFirstname`                                            | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `artArtLastname`                                             | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `artArtYear`                                                 | int(4)                                   | yes      | yes       |          | Year of birth                                                                    |
| `artArtRatingFormula`                                        | text(1000)                               |          | yes       |          | -                                                                                |

`ArtOwner` business object definition
-------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artOwnName`                                                 | char(255)                                | yes*     | yes       |          | -                                                                                |

`ArtPlace` business object definition
-------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artPlcName`                                                 | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `artPlcPublic`                                               | boolean                                  |          | yes       |          | -                                                                                |

`ArtTechnique` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artTecName`                                                 | char(250)                                | yes*     | yes       |          | -                                                                                |

`ArtPiece` business object definition
-------------------------------------

Piece of art

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artPicTitle`                                                | char(200)                                |          | yes       |          | -                                                                                |
| `artPicArtistId` link to **`ArtArtist`**                     | id                                       |          | yes       |          | -                                                                                |
| _Ref. `artPicArtistId.artArtFirstname`_                      | _char(50)_                               |          |           |          | -                                                                                |
| _Ref. `artPicArtistId.artArtLastname`_                       | _char(50)_                               |          |           |          | -                                                                                |
| _Ref. `artPicArtistId.artArtRatingFormula`_                  | _text(1000)_                             |          |           |          | -                                                                                |
| `artPicYear`                                                 | int(4)                                   |          | yes       |          | -                                                                                |
| `artPicTecId` link to **`ArtTechnique`**                     | id                                       |          | yes       |          | -                                                                                |
| _Ref. `artPicTecId.artTecName`_                              | _char(250)_                              |          |           |          | -                                                                                |
| `artPicTechnique`                                            | enum(20) using `ARTPICTECHNIQUE` list    | yes      |           |          | -                                                                                |
| `artPicHeight`                                               | float(7, 2)                              |          | yes       |          | -                                                                                |
| `artPicWidth`                                                | float(7, 2)                              |          | yes       |          | -                                                                                |
| `artPicSigned`                                               | enum(1) using `ART_ENUM_BOOL` list       | yes      | yes       |          | -                                                                                |
| `artPicPicture`                                              | image                                    |          | yes       |          | -                                                                                |
| `artPicState`                                                | enum(30) using `ARTPICSTATE` list        | yes      | yes       |          | -                                                                                |
| `artPicCode`                                                 | char(20)                                 | yes*     | yes       |          | -                                                                                |
| `artPicCodeBis`                                              | char(20)                                 |          | yes       |          | -                                                                                |
| `artPicEntry`                                                | date                                     |          | yes       |          | -                                                                                |
| `artPicPlaceId` link to **`ArtPlace`**                       | id                                       |          | yes       |          | -                                                                                |
| _Ref. `artPicPlaceId.artPlcName`_                            | _char(50)_                               |          |           |          | -                                                                                |
| `artPicOwnId` link to **`ArtOwner`**                         | id                                       | yes      | yes       |          | -                                                                                |
| _Ref. `artPicOwnId.artOwnName`_                              | _char(255)_                              |          |           |          | -                                                                                |
| `artPicPrice`                                                | float(9, 2)                              |          | yes       |          | Estimated price                                                                  |
| `artPicEstimatedPrice`                                       | float(100, 2)                            |          | yes       |          | -                                                                                |
| `artPicComment`                                              | text(1000)                               |          | yes       |          | -                                                                                |
| `artPicDocument`                                             | document                                 |          | yes       |          | -                                                                                |
| `artPicScore`                                                | float(5, 2)                              |          |           |          | -                                                                                |
| `artPicExbVirtual` link to **`ArtExhibition`**               | id                                       |          | yes       |          | -                                                                                |
| `artPicTagVirtual` link to **`ArtTag`**                      | id                                       |          | yes       |          | -                                                                                |

### Lists

* `ARTPICTECHNIQUE`
    - `NULL` Unknown
    - `LIN` Óleo sobre tela y lino
    - `PINTEX` Pintura Textil
    - `OIL` Oil on canvas
    - `LUNGI` Técnica Mixta (y óleo) sobre tela (Lungi)
* `ART_ENUM_BOOL`
    - `2` NULL
    - `1` TRUE
    - `0` FALSE
* `ARTPICSTATE`
    - `SELLING` En vente
    - `SOLD` Vendu
    - `STORED` Stocké

### Custom actions

* `calculateEstimatedPrice`: 
* `reIndexAll`: 

`ArtExhibition` business object definition
------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artExbDate`                                                 | date                                     | yes*     | yes       |          | -                                                                                |
| `artExbPlcId` link to **`ArtPlace`**                         | id                                       |          | yes       |          | -                                                                                |
| _Ref. `artExbPlcId.artPlcName`_                              | _char(50)_                               |          |           |          | -                                                                                |
| `artExbCommissaire`                                          | char(100)                                |          | yes       |          | -                                                                                |
| `artExbDescrition`                                           | html(10000)                              |          | yes       |          | -                                                                                |

`ArtExbPic` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artExbpicExbId` link to **`ArtExhibition`**                 | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artExbpicExbId.artExbDate`_                           | _date_                                   |          |           |          | -                                                                                |
| `artExbpicPicId` link to **`ArtPiece`**                      | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artExbpicPicId.artPicCode`_                           | _char(20)_                               |          |           |          | -                                                                                |

`ArtTag` business object definition
-----------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artTagLabel`                                                | char(20)                                 | yes*     | yes       |          | -                                                                                |
| `artTagPublic`                                               | boolean                                  |          | yes       |          | -                                                                                |

`ArtTagPiece` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artTpTagId` link to **`ArtTag`**                            | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artTpTagId.artTagLabel`_                              | _char(20)_                               |          |           |          | -                                                                                |
| `artTpPieceId` link to **`ArtPiece`**                        | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artTpPieceId.artPicCode`_                             | _char(20)_                               |          |           |          | -                                                                                |

`ArtDocument` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `artDocPicId` link to **`ArtPiece`**                         | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artDocPicId.artPicCode`_                              | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `artDocPicId.artPicTitle`_                             | _char(200)_                              |          |           |          | -                                                                                |
| `artDocDescription`                                          | char(200)                                | yes*     | yes       |          | -                                                                                |
| `artDocFile`                                                 | document                                 | yes      | yes       |          | -                                                                                |

`ArtImageRecognition` external object definition
------------------------------------------------




