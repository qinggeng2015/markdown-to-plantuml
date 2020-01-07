# PlantUML转换器

可以在markdown中使用PlantUML绘制流程图、时序图、用例图、类图等等。

在Typora上可以正常显示。

例子：

![时序图](http://139.199.126.68:8080/png?
    @startuml
    Alice -> Bob: Authentication Request
    Bob --> Alice: Authentication Response
    Alice -> Bob: Another authentication Request
    Alice <-- Bob: another authentication Response
    @enduml
)

![类图]('http://139.199.126.68:8080/png?
@startuml
Class01 "1" *-- "many" Class02 : contains
Class03 o-- Class04 : aggregation
Class05 --> "1" Class06
@enduml
')

<img src='http://139.199.126.68:8080/png?
    @startuml
    Alice -> Bo1111123111111b: Authentication Request
    Bob --> Alice: Authentication Response
    Alice -> Bob: Another authentication Request
    Alice <-- Bob111: another authentication Response
    @enduml
)' align="left"></img>
