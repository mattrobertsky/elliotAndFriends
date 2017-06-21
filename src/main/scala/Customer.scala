import scala.collection.mutable.ListBuffer

/**
  * Created by matt on 20/06/17.
  */
class Customer(name: String) extends Person(name) with IdAble {

  val basket: ListBuffer[Item] = new ListBuffer[Item]()

  var rewardPoints: Int = 0
  val id = nextId()

  def nextId(): String = {
    "CUS-" + IdAble.nextIndex()
  }

  def addToBasket(item: Item) = {
    this.basket += item
  }

  def emptyBasket: Unit = {
    this.basket.remove(0, basket.size)
  }

}

