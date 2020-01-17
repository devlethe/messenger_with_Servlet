CREATE TABLE USER_INFO 
(
  IDX Number(10) CONSTRAINT USER_INFO_PK PRIMARY KEY 
, USER_ID VARCHAR2(20)  UNIQUE NOT NULL
, USER_PASSWORD VARCHAR2(20) 
, DEVICE_ID VARCHAR2(50) 
, USER_NAME VARCHAR2(20) 
, USER_NICKNAME VARCHAR2(20) 
, USER_IMAGE BLOB 
, USER_STATUS_MSG VARCHAR2(100) 
, USER_CREATE_TIMESTAMP TIMESTAMP(4) WITH LOCAL TIME ZONE DEFAULT SYSDATE NOT NULL
);
CREATE TABLE MESSAGE
(
    idx              Number(10)   CONSTRAINT MESSAGE_PK PRIMARY KEY, 
    msg_from         VARCHAR2(20)    , 
    msg_to           VARCHAR2(20)    , 
    msg_content      VARCHAR2(20)    , 
    msg_image        BLOB            , 
    msg_timestamp    TIMESTAMP(4) WITH LOCAL TIME ZONE DEFAULT SYSDATE NOT NULL,
    msg_room NUMBER(10,0) NOT NULL 
);
CREATE TABLE message_room
(
    idx              Number(10)   CONSTRAINT MESSAGE_ROOM_PK PRIMARY KEY, 
    room_from         VARCHAR2(20)    , 
    room_to           VARCHAR2(20)    
);

CREATE TABLE FRIEND
(
    idx            Number(10)   CONSTRAINT FRIEND_PK PRIMARY KEY, 
    user_id        VARCHAR2(20)    , 
    user_friend    VARCHAR2(20)    , 
    user_type      VARCHAR2(20)
);

