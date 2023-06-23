# SRTtoScript
index, timestamp 로 구분된 .srt 파일을 script 파일(주로 .txt)로 변환하는 프로그램입니다.

영어 공부를 목적으로 유튜브나 영화의, 영문 자막 파일(.srt)을 참조했을 때 index, timestamp 가 읽기 거슬리고, 
문장별 구분이 되어있지 않는 문제를 해결하기 위해서 만든 프로젝트입니다.

단순히 index 와 timestamp 만 제거하는게 아니라 분할된 자막을 StanfordCoreNLP 라이브러리를 사용해서 하나의 문장으로 만듭니다.

A project to convert .srt files into script (.txt) files, helping you extract text from .srt files.
It also reconstructs complete sentences from fragmented words within the .srt files.



###From
```
15
00:00:33,090 --> 00:00:35,100
- What inspired you to become a Pastor?

16
00:00:35,100 --> 00:00:39,210
- It's a funny story. I really
  didn't desire to be a pastor.

17
00:00:39,210 --> 00:00:40,800
I wanted to be a police officer.

18
00:00:40,800 --> 00:00:43,020
And so I always joke when I'm preaching

19
00:00:43,020 --> 00:00:44,640
that I got the peas mixed up.
```

###Into
```
- What inspired you to become a Pastor?
- It's a funny story. I really didn't desire to be a pastor.
I wanted to be a police officer.
And so I always joke when I'm preaching that I got the peas mixed up.
```

# Logic
1. Read an .srt file
2. Remove the indexes and timestamps from the original file.
3. Utilize the StanfordCoreNLP library to identify sentences and consolidate the words together.

# Todo
1. make String into a .txt file
2. make some UIs for the application.

# Libraries
- StanfordCoreNLP