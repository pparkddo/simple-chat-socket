# simple-chat-socket
간단한 서버-클라이언트 소켓 채팅 어플리케이션  
클라이언트에서 서버로 메시지를 전송하면 서버가 같은 채팅방 안에 있는 클라이언트들에게 메시지를 복사전송합니다.
![simple-chat-socket](https://gist.githubusercontent.com/pparkddo/4375d9d7b5972d641da7c088cc913a6a/raw/3238b0267acc65cfe87adba4f22f55ffef0fafbc/simple-chat-socket.svg)

## Structure
- 멀티쓰레드서버 (클라이언트당 하나의 쓰레드를 생성)
![simple-chat-socket-server](https://gist.githubusercontent.com/pparkddo/4375d9d7b5972d641da7c088cc913a6a/raw/3238b0267acc65cfe87adba4f22f55ffef0fafbc/simple-chat-socket-server.svg)

# Installation
```
git clone https://github.com/pparkddo/simple-chat-socket.git
gradle build
```


## Feature
- 그룹 채팅
- 파일 전송