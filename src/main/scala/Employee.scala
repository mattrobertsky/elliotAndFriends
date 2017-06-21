/**
  * Created by Administrator on 19/06/2017.
  */
class Employee(name:String, var isManager:Boolean) extends Person(name) with IdAble {

  val id = this.nextId()

  override def nextId(): String = {
    "EMP-" + IdAble.nextIndex()
  }
}

