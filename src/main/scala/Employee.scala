/**
  * Created by Administrator on 19/06/2017.
  */
class Employee(name:String, var isManager:Boolean) extends Person(name) {

  val id = nextId()

  override def nextId(): String = {
    seq += 1
    "EMP-" + seq
  }
}
