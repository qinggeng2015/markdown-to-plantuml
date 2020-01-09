# PlantUML转换器

可以在markdown中使用PlantUML绘制流程图、时序图、用例图、类图等等。

注意：
1. 换行后每行前需要预留四个空格，而不是tab。因为在发出网络请求时换行符和tab会被丢弃，通过四个空格来判断是否是下一行
2. 箭头不能是指向左面的，例如：<-- 。会被chrome拦截这种请求，认为img标签的<>是不完整的，但是在其他的浏览器中没有问题
3. 建议使用img标签而不是![]() ，兼容性略好

例子：

```
<img src='http://139.199.126.68:8080/svg?
    @startuml
    actor Foo1
    boundary Foo2
    control Foo3
    entity Foo4
    database Foo5
    collections Foo6
    Foo1 -> Foo2 : To boundary
    Foo1 -> Foo3 : To control
    Foo1 -> Foo4 : To entity
    Foo1 -> Foo5 : To database
    Foo1 -> Foo6 : To collections
    @enduml
'/>
```
效果：

<img src='http://139.199.126.68:8080/svg?
    @startuml
    actor Foo1
    boundary Foo2
    control Foo3
    entity Foo4
    database Foo5
    collections Foo6
    Foo1 -> Foo2 : To boundary
    Foo1 -> Foo3 : To control
    Foo1 -> Foo4 : To entity
    Foo1 -> Foo5 : To database
    Foo1 -> Foo6 : To collections
    @enduml
'/>

```
<img src='http://139.199.126.68:8080/svg?
    @startuml
    actor Foo1
    boundary Foo2
    control Foo3
    entity Foo4
    database Foo5
    collections Foo6
    Foo1 -> Foo2 : To boundary
    Foo1 -> Foo3 : To control
    Foo1 -> Foo4 : To entity
    Foo1 -> Foo5 : To database
    Foo1 -> Foo6 : To collections
    @enduml
'/>
```

效果：

<img src='http://139.199.126.68:8080/svg?
    @startuml
    Class01 --> Class02
    Class03 *-- Class04
    Class05 o-- Class06
    Class07 .. Class08
    Class09 -- Class10
    @enduml
'>

```
<img src='http://139.199.126.68:8080/svg?
    @startuml
    interface Observer{
            update()
    }
    note right of Observer
        所有的观察者必须实现这个接口，
        当主题状态发生改变时，
        nupdate方法被调用。
    end note
    interface Subject{
        registerObserver()
        removeObserver()
        notifyObserers()
    }
    class ConcreteSubject{
            registerObserver()
            removeOberver()
            notifyObservers()
            getState()
            setState()
    }
    class ConcreteObserver{
            update()
    }
    note right of ConcreteObserver
        观察者必须注册具体主题，
        以便接受更新。
    end note
    ConcreteSubject .up.|> Subject
    Subject "1" -> "n" Observer : 许多观察者
    ConcreteObserver -left> ConcreteSubject : 订阅主题
    ConcreteObserver .up.|> Observer
    @enduml
'>
```

效果：

<img src='http://139.199.126.68:8080/svg?
    @startuml
    interface Observer{
            update()
    }
    note right of Observer
        所有的观察者必须实现这个接口，
        当主题状态发生改变时，
        nupdate方法被调用。
    end note
    interface Subject{
        registerObserver()
        removeObserver()
        notifyObserers()
    }
    class ConcreteSubject{
            registerObserver()
            removeOberver()
            notifyObservers()
            getState()
            setState()
    }
    class ConcreteObserver{
            update()
    }
    note right of ConcreteObserver
        观察者必须注册具体主题，
        以便接受更新。
    end note
    ConcreteSubject .up.|> Subject
    Subject "1" -> "n" Observer : 许多观察者
    ConcreteObserver -left> ConcreteSubject : 订阅主题
    ConcreteObserver .up.|> Observer
    @enduml
'>