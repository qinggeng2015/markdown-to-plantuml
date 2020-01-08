# PlantUML转换器

可以在markdown中使用PlantUML绘制流程图、时序图、用例图、类图等等。

在Typora上可以正常显示。

例子：

![时序图](http://139.199.126.68:8080/svg?
    @startuml
    Alice -> Bo112111121111b: Authentication Request
    Bob --> Alice: Authentication Response
    Alice -> Bob: Another authentication Request
    Alice <-- Bob11: another authentication Response
    @enduml
)