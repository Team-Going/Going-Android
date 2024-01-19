<img src="https://github.com/Team-Going/Going-Android/assets/97405341/9d8f8e2b-f3f6-4773-813b-49d3a9a86432" width=120 />

# doorip
<p align="center"><img src="https://github.com/Team-Going/Going-Android/assets/97405341/a49585b0-e4d3-4713-b5de-0e61aede220d"></p>

**서로를 이해하며 완성해가는 우리의 여행 To-do, Doorip**

*여행자 10명 중 6명은 여행을 같이 간 사람과 갈등을 겪고*, 다음 여행에서는 누군가와 함께 하는 여행 자체를 망설이게 됩니다.
</br>
고잉고잉은 이 문제를 해결할, 쉽고 재밌는 솔루션을 만드는 팀입니다. 

doorip에서 **여행에서 공동 할일 관리에 대한 불편과 부담을 내려놓고 좋아하는 사람들과 여행을 오롯이 즐기세요.**

여행 중에 생기는 모든 할 일을 함께 관리하세요. doorip의 취향태그와 유형테스트로 서로를 이해할 수 있어요.

<img height="20px" src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white"/> <img height="20px" src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white">

<br> 

## 두릅이들
|                                   김상호<br/>([@Marchbreeze](https://github.com/Marchbreeze))                                    |                                      박동민<br/>([@chattymin](https://github.com/chattymin))                                       |                                  이유빈<br/>([@leeeyubin](https://github.com/leeeyubin))                                   |                                    조세연<br/>([@crownjoe](https://github.com/crownjoe))                                     |
|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|
| <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/68834cbc-67c6-4f21-a010-63c7e440410e"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/04c3f595-d8cf-4d4a-a6c6-bd2e6d4c9a43"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/1c0f5866-8a39-445d-9664-3d3fc76bb39d"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/1269e602-32db-4cb6-a91a-8b80c8a9730e"/> |
|                                                      `공동 투두`<br/>`개인 투두`<br/>`할일 추가/조회`                                                      |                                                         `스플래쉬`<br/>`온보딩`<br/>`여행 성향`                                                          |                                          `대시보드`<br/>`취향 태그`<br/>`공동 취향`                                        |                                                      `여행 생성`<br/>`여행 입장`<br/>`프로필`                                                      |



<br>

## MODULE & PACKAGE CONVENTION
```

🗃️app
 ┗ 📂di

🗃️buildSrc

🗃️core-ui
 ┣ 📂base
 ┗ 📂extension

🗃️data
 ┣ 📂dto
 ┃ ┣ 📂response
 ┃ ┣ 📂request
 ┣ 📂datasource
 ┣ 📂datasourceImpl
 ┣ 📂interceptor
 ┣ 📂local
 ┣ 📂repositoryImpl
 ┗ 📂service

🗃️domain
 ┣ 📂entity
 ┃ ┣ 📂response
 ┃ ┣ 📂request
 ┗ 📂repository

🗃️presentation
 ┗ 📂기능 별 패키징

```
<br>

## TECH STACK
- Multi-Module
- Android App Architecture
- Hilt
- Coroutine & Flow
- Data Binding
- Timber, Coil, Lottie
- Kakao Open API
<br>


## SCREENSHOTS
|       뷰       |                                                              1                                                              |                                                              2                                                              |                                                              3                                                              |                                                              4                                                              |
|:-------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|
| 온보딩 <br> 성향검사 | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/f79733e0-d4ff-4755-adcc-b334e90a5c62"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/dec2b462-6618-4917-8d03-e7d8542dffb3"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/f5b18987-6953-4b14-8ebd-296bb5d5f82b"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/d84e184e-5397-4825-9485-1b9766830cc9"/> |                                                                                                                                 |                                                                                                                             |
| 여행생성 <br> 여행입장 <br> 대시보드  | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/e62ea373-914f-4c35-94ff-6eb9ed79580e"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/692a00d3-2f4e-43fb-b707-480c36d08b54"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/1d684b23-7712-44c0-ae38-8f0f258fa880"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/1c596bf7-9cc6-455b-b5c7-90fdf82dbe33"/> |
| 아워투두 <br> 개인투두  | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/7d5e0481-6527-4ce4-b12f-f6a4237f12bf"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/e5a8d79c-b014-4750-957f-0ccafd35d763"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/89c270e6-f17a-4f14-99e2-b41f230a4477"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/7146f1f5-cbd7-46db-b4ed-066ed451f7de"/> |
|   프로필 <br> 설정   | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/c34fb56c-a54c-47b2-9c0a-d18be63ae1de"/> | <img width="200px" src="https://github.com/Team-Going/Going-Android/assets/97405341/96234e32-2980-488f-bb9f-3b92afa504fa"/> | 

<br>



## KANBAN BOARD
- [GOINGGOING PROJECT](https://github.com/orgs/Team-Going/projects/2)
<br>  

## CONVENTION
- [GITHUB CONVENTION](https://www.notion.so/goinggoing/Github-Convention-29f0af1cd4944705997594859e28ff97?pvs=4)
- [NAMING CONVENTION](https://www.notion.so/goinggoing/Naming-Convention-8ff5cb317e31404aa23d5729473e5b5b?pvs=4)
- [PACKAGE CONVENTION](https://www.notion.so/goinggoing/Packaging-Convention-c390c0a561cf4b469bfac2da58ebf445?pvs=4)
- [APPJAM TIMELINE](https://www.notion.so/goinggoing/50fb75bea22143abb589b0c37aba6ea3?v=f6982004cc434a4fb2dcd0979b2a8c50&pvs=4)
<br>
