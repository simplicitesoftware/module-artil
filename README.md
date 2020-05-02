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



`ArtArtist` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artArtFirstname`                                            | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `artArtLastname`                                             | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `artArtYear`                                                 | int(4)                                   | yes      | yes       |          | Year of birth                                                                    |

### Custom actions

No custom action

`ArtDocument` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artDocPicId` link to **`ArtPiece`**                         | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artDocPicId.artPicCode`_                              | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `artDocPicId.artPicTitle`_                             | _char(200)_                              |          |           |          | -                                                                                |
| `artDocDescription`                                          | char(200)                                | yes*     | yes       |          | -                                                                                |
| `artDocFile`                                                 | document                                 | yes      | yes       |          | -                                                                                |

### Custom actions

No custom action

`ArtExbPic` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artExbpicExbId` link to **`ArtExhibition`**                 | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artExbpicExbId.artExbDate`_                           | _date_                                   |          |           |          | -                                                                                |
| `artExbpicPicId` link to **`ArtPiece`**                      | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artExbpicPicId.artPicCode`_                           | _char(20)_                               |          |           |          | -                                                                                |

### Custom actions

No custom action

`ArtExhibition` business object definition
------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artExbDate`                                                 | date                                     | yes*     | yes       |          | -                                                                                |
| `artExbPlcId` link to **`ArtPlace`**                         | id                                       |          | yes       |          | -                                                                                |
| _Ref. `artExbPlcId.artPlcName`_                              | _char(50)_                               |          |           |          | -                                                                                |
| `artExbCommissaire`                                          | char(100)                                |          | yes       |          | -                                                                                |
| `artExbDescrition`                                           | html(10000)                              |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`ArtPiece` business object definition
-------------------------------------

Piece of art

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artPicScore`                                                | float(5, 2)                              |          |           |          | -                                                                                |
| `artPicArtistId` link to **`ArtArtist`**                     | id                                       |          | yes       |          | -                                                                                |
| _Ref. `artPicArtistId.artArtFirstname`_                      | _char(50)_                               |          |           |          | -                                                                                |
| _Ref. `artPicArtistId.artArtLastname`_                       | _char(50)_                               |          |           |          | -                                                                                |
| `artPicCode`                                                 | char(20)                                 | yes*     | yes       |          | -                                                                                |
| `artPicTitle`                                                | char(200)                                |          | yes       |          | -                                                                                |
| `artPicTechnique`                                            | enum(7) using `ARTPICTECHNIQUE` list     | yes      | yes       |          | -                                                                                |
| `artPicHeight`                                               | float(7, 2)                              |          | yes       |          | -                                                                                |
| `artPicWidth`                                                | float(7, 2)                              |          | yes       |          | -                                                                                |
| `artPicYear`                                                 | int(4)                                   |          | yes       |          | -                                                                                |
| `artPicPrice`                                                | float(9, 2)                              |          | yes       |          | Estimated price                                                                  |
| `artPicPlaceId` link to **`ArtPlace`**                       | id                                       |          | yes       |          | -                                                                                |
| _Ref. `artPicPlaceId.artPlcName`_                            | _char(50)_                               |          |           |          | -                                                                                |
| `artPicEntry`                                                | date                                     |          | yes       |          | -                                                                                |
| `artPicComment`                                              | text(1000)                               |          | yes       |          | -                                                                                |
| `artPicPicture`                                              | image                                    |          | yes       |          | -                                                                                |
| `artPicDocument`                                             | document                                 |          | yes       |          | -                                                                                |
| `artPicSigned`                                               | enum(7) using `ART_ENUM_BOOL` list       | yes      | yes       |          | -                                                                                |
| `artPicTagVirtual` link to **`ArtTag`**                      | id                                       |          | yes       |          | -                                                                                |
| `artPicExbVirtual` link to **`ArtExhibition`**               | id                                       |          | yes       |          | -                                                                                |
| `artPicCodeBis`                                              | char(20)                                 |          | yes       |          | -                                                                                |

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

### Custom actions

* `reIndexAll`: 

`ArtPlace` business object definition
-------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artPlcName`                                                 | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `artPlcPublic`                                               | boolean                                  |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`ArtTag` business object definition
-----------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artTagLabel`                                                | char(20)                                 | yes*     | yes       |          | -                                                                                |
| `artTagPublic`                                               | boolean                                  |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`ArtTagPiece` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `artTpTagId` link to **`ArtTag`**                            | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artTpTagId.artTagLabel`_                              | _char(20)_                               |          |           |          | -                                                                                |
| `artTpPieceId` link to **`ArtPiece`**                        | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `artTpPieceId.artPicCode`_                             | _char(20)_                               |          |           |          | -                                                                                |

### Custom actions

No custom action

`ArtImageRecognition` external object definition
------------------------------------------------




