基础设施

所有的中间件工具包都在这个里面进行配置启动；



> 整合ES

添加数据

PUT testdb/_doc/3
{
  "t1":"23",
  "t2":"2021-2-1"
}


PUT testdb/_doc/4
{
  "t1":"33",
  "t2":"2033-2-1"
}

简单查询
```aidl
GET testdb/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "term": {
           "t1":23
          }
        },
       {
         "term": {
          "t1":33
         }
       }
      ]
    }
  }
}
```
或查询 should
```aidl

GET testdb/_search
{
  "query": {
    "bool": {
      "should": [
        {
          "term": {
           "t1":23
          }
        },
       {
         "term": {
          "t1":33
         }
       }
      ]
    }
  }
}

```

高亮查询
```aidl

GET testdb/_search
{
  "query": {
    "match": {
      "name": "狂"
    }
  },
  "highlight": {
    "fields": {
      "name": {}
    }
  }
}
```
自定义高亮样式
```aidl

GET testdb/_search
{
  "query": {
    "match": {
      "name": "狂"
    }
  },
  "highlight": {
    "pre_tags": "<p class='key' style='color:red'>",
    "post_tags": "</p>" ,
    "fields": {
      "name": {}
    }
  }
}
```