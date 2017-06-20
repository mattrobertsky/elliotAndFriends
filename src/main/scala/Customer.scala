
/**
  * Created by matt on 20/06/17.
  */
class Customer(name: String) extends Person(name) with IdAble {
  var rewardPoints: Int = 0
  val id = nextId()

  def nextId(): String = {
    "CUS-" + IdAble.nextIndex()
  }

}

