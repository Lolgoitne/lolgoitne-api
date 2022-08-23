# lolgoitne-api
카카오톡 봇 롤고있네의 API 서버 입니다!

채널 주소: https://pf.kakao.com/_JVUdxb

### Why
카카오톡이 안깔려있는 핸드폰이 거의 없는 한국인인에게 카톡 봇이 최고의 접근성이 될 것이라고 생각했기 때문

### Target
게임 중에 연락이 안 되는 친구

# Router
- [POST] /api/bot/check-game 유저 닉네임으로 해당 유저가 게임 중인지 확인할 수 있는 API
- [POST] /api/bot/game-in-7days 유저 닉네임으로 해당 유저가 지난 1주간 게임을 얼마나 했는지 확인할 수 있는 API

# Getting Start!
1. adapter-in/src/main/resources/application.properties 에 존재하는 riot.apiKey 를 라이엇 API 에서 발급받아서 붙여넣는다.
2. common/src/main/resources/generateKeyPair.sh 를 실행시켜 jwt secret 을 위한 .pem 파일을 만들어준다.
3. gradle :adapter-in:bootrun

# Archihecture
Adapter-in - API, 외부에서 들어오는 요청들을 핸들링 하는 레이어

Adapter-out - Http Client 등 흐름이 내부에서 외부로 나가는 레이어

core - port, service, domain 으로 이루어지는 핵심 레이어

common - jwt 와 같이 유틸성 모듈들이 존재하는 레이어

