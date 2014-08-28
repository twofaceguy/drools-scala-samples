package org.drools.examples.petstore

import java.awt.event.{MouseEvent, MouseAdapter}
import java.awt.{GridLayout, Dimension, BorderLayout}
import java.util
import javax.swing._
import javax.swing.table.{AbstractTableModel, DefaultTableCellRenderer, TableColumnModel}

import org.kie.api.KieServices
import org.kie.api.runtime.{KieContainer, KieSession}

import scala.beans.BeanProperty
import scala.collection.mutable.{ArrayBuffer => AB}

/**
 * Created by Simon on 2014/8/28
 */
object PetStoreExample extends App{
  val ks: KieServices = KieServices.Factory.get
  // From the kie services, a container is created from the classpath
  val kc: KieContainer = ks.getKieClasspathContainer
  //RuleB
  val stock  =  new util.Vector[Product]()
  stock.add(new Product("Gold Fish", 5))


  stock.add(new Product("Fish Tank", 25))


  stock.add(new Product("Fish Food", 2))
  //The callback is responsible for populating working memory and
  // fireing all rules
  val ui: PetStoreUI = new PetStoreUI(stock, new CheckoutCallback(kc))
  ui.createAndShowGUI(exitOnClose = true)
}


case class Product(@BeanProperty name: String, @BeanProperty price: Double){
  override def hashCode: Int = {
    val PRIME: Int = 31
    var result: Int = 1
    result = PRIME * result + (if (name == null) 0 else name.hashCode)
    var temp: Long = 0L
    temp = java.lang.Double.doubleToLongBits(price)
    result = PRIME * result + (temp ^ (temp >>> 32)).asInstanceOf[Int]
    result
  }
  override def equals(obj: Any): Boolean = {
    //if (this eq obj) return true
    if (obj == null) return false
    if (getClass ne obj.getClass) return false
    val other: Product = obj.asInstanceOf[Product]
    if (name == null) {
      if (other.name != null) return false
    }
    else if (!(name == other.name)) return false
    if (java.lang.Double.doubleToLongBits(price) != java.lang.Double.doubleToLongBits(other.price)) return false
     true
  }
}

case class Purchase(@BeanProperty order: Order, @BeanProperty product: Product){
  override def hashCode: Int = {
    val PRIME: Int = 31
    var result: Int = 1
    result = PRIME * result + (if (order == null) 0 else order.hashCode)
    result = PRIME * result + (if (product == null) 0 else product.hashCode)
    result
  }

  override def equals(obj: Any): Boolean = {
    //if (this eq obj) return true
    if (obj == null) return false
    if (getClass ne obj.getClass) return false
    val other: Purchase = obj.asInstanceOf[Purchase]
    if (order == null) {
      if (other.order != null) return false
    }
    else if (!(order == other.order)) return false
    if (product == null) {
      if (other.product != null) return false
    }
    else if (!(product == other.product)) return false
    true
  }

}

class Order(@BeanProperty var grossTotal: Double = -1,
                 @BeanProperty var discountedTotal: Double = -1,
                 @BeanProperty val items: util.ArrayList[Purchase] = new util.ArrayList[Purchase]()) {
  def addItem(item: Purchase) {
    items.add(item)
  }
}

/**
 * This swing UI is used to create a simple shopping cart to allow a user to add
 * and remove items from a shopping cart before doign a checkout upon doing a
 * checkout a callback is used to allow drools interaction with the shopping
 * cart ui.
 */
object PetStoreUI {
  private final val serialVersionUID: Long = 510l
}

class PetStoreUI(items: java.util.Vector[Product], callback: CheckoutCallback) extends JPanel(new BorderLayout) {


  private var output: JTextArea = null
  private var tableModel: TableModel = null
  init()


  def init() = {
    val splitPane: JSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT)
    add(splitPane, BorderLayout.CENTER)
    val topHalf: JPanel = new JPanel
    topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.X_AXIS))
    topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5))
    topHalf.setMinimumSize(new Dimension(400, 50))
    topHalf.setPreferredSize(new Dimension(450, 250))
    splitPane.add(topHalf)
    val bottomHalf: JPanel = new JPanel(new BorderLayout)
    bottomHalf.setMinimumSize(new Dimension(400, 50))
    bottomHalf.setPreferredSize(new Dimension(450, 300))
    splitPane.add(bottomHalf)
    val listContainer: JPanel = new JPanel(new GridLayout(1, 1))
    listContainer.setBorder(BorderFactory.createTitledBorder("List"))
    topHalf.add(listContainer)
    val list = new JList[Product](items)
    val listSelectionModel: ListSelectionModel = list.getSelectionModel
    listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
    list.addMouseListener(ListSelectionHandler)
    val listPane: JScrollPane = new JScrollPane(list)
    listContainer.add(listPane)
    val tableContainer: JPanel = new JPanel(new GridLayout(1, 1))
    tableContainer.setBorder(BorderFactory.createTitledBorder("Table"))
    topHalf.add(tableContainer)
    tableModel = new TableModel
    val table: JTable = new JTable(tableModel)
    table.addMouseListener(TableSelectionHandler)
    val tableSelectionModel: ListSelectionModel = table.getSelectionModel
    tableSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
    val tableColumnModel: TableColumnModel = table.getColumnModel
    tableColumnModel.getColumn(0).setCellRenderer(NameRenderer)
    tableColumnModel.getColumn(1).setCellRenderer(PriceRenderer)
    tableColumnModel.getColumn(1).setMaxWidth(50)
    val tablePane: JScrollPane = new JScrollPane(table)
    tablePane.setPreferredSize(new Dimension(150, 100))
    tableContainer.add(tablePane)
    val checkoutPane: JPanel = new JPanel
    var button: JButton = new JButton("Checkout")
    button.setVerticalTextPosition(SwingConstants.CENTER)
    button.setHorizontalTextPosition(SwingConstants.LEADING)
    button.addMouseListener(CheckoutButtonHandler)
    button.setActionCommand("checkout")
    checkoutPane.add(button)
    bottomHalf.add(checkoutPane, BorderLayout.NORTH)
    button = new JButton("Reset")
    button.setVerticalTextPosition(SwingConstants.CENTER)
    button.setHorizontalTextPosition(SwingConstants.TRAILING)
    button.addMouseListener(ResetButtonHandler)
    button.setActionCommand("reset")
    checkoutPane.add(button)
    bottomHalf.add(checkoutPane, BorderLayout.NORTH)
    output = new JTextArea(1, 10)
    output.setEditable(false)
    val outputPane: JScrollPane = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED)
    bottomHalf.add(outputPane, BorderLayout.CENTER)
    callback.setOutput(this.output)
  }


  /**
   * Create and show the GUI
   */
  def createAndShowGUI(exitOnClose: Boolean) {
    val frame: JFrame = new JFrame("Pet Store Demo")
    frame.setDefaultCloseOperation(if (exitOnClose) JFrame.EXIT_ON_CLOSE else WindowConstants.DISPOSE_ON_CLOSE)
    setOpaque(true)
    frame.setContentPane(this)
    frame.pack
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

  /**
   * Adds the selected item to the table
   */
  private object ListSelectionHandler extends MouseAdapter {
    override def mouseReleased(e: MouseEvent) {
      val jlist: JList[_] = e.getSource.asInstanceOf[JList[_]]
      tableModel.addItem(jlist.getSelectedValue.asInstanceOf[Product])
    }
  }

  /**
   * Removes the selected item from the table
   */
  private object TableSelectionHandler extends MouseAdapter {
    override def mouseReleased(e: MouseEvent) {
      val jtable: JTable = e.getSource.asInstanceOf[JTable]
      val tableModel: TableModel = jtable.getModel.asInstanceOf[TableModel]
      tableModel.removeItem(jtable.getSelectedRow)
    }
  }

  /**
   * Calls the referenced callback, passing a the jrame and selected items.
   */
  private object CheckoutButtonHandler extends MouseAdapter {
    override def mouseReleased(e: MouseEvent) {
      val button: JButton = e.getComponent.asInstanceOf[JButton]
      callback.checkout(button.getTopLevelAncestor.asInstanceOf[JFrame], tableModel.getItems)
    }
  }

  /**
   * Resets the shopping cart, allowing the user to begin again.
   */
  private object ResetButtonHandler extends MouseAdapter {
    override def mouseReleased(e: MouseEvent) {
      output.setText(null)
      tableModel.clear()
      System.out.println("------ Reset ------")
    }
  }

  /**
   * Used to render the name column in the table
   */
  private object NameRenderer extends DefaultTableCellRenderer {
    private final val serialVersionUID: Long = 510l
    override def setValue(`object`: AnyRef) {
      val item: Product = `object`.asInstanceOf[Product]
      setText(item.getName)
    }
  }

  /**
   * Used to render the price column in the table
   */
  private object PriceRenderer extends DefaultTableCellRenderer {
    private final val serialVersionUID: Long = 510l
    override def setValue(`object`: AnyRef) {
      val item: Product = `object`.asInstanceOf[Product]
      setText(item.price.toString)
    }
  }

}

/**
 * This is the table model used to represent the users shopping cart While
 * we have two colums, both columns point to the same object. We user a
 * different renderer to display the different information abou the object -
 * name and price.
 */
private object TableModel {
  private final val serialVersionUID: Long = 510l
}

private class TableModel extends AbstractTableModel {


  private val columnNames: Array[String] = Array("Name", "Price")
  private val items = scala.collection.mutable.ArrayBuffer[Product]()


  def getColumnCount: Int = {
    columnNames.length
  }

  def getRowCount: Int = {
    items.size
  }

  override def getColumnName(col: Int): String = {
    columnNames(col)
  }

  def getValueAt(row: Int, col: Int): AnyRef = {
    items(row)
  }

  override def getColumnClass(c: Int): Class[_] = {
    classOf[Product]
  }

  def addItem(item: Product) {
    items.append(item)
    fireTableRowsInserted(items.size, items.size)
  }

  def removeItem(row: Int) {
    items.remove(row)
    fireTableRowsDeleted(row, row)
  }

  def getItems: List[Product] = items.toList

  def clear() {
    val lastRow: Int = items.size
    items.clear()
    fireTableRowsDeleted(0, lastRow)
  }
}

/**
 *
 * This callback is called when the user pressed the checkout button. It is
 * responsible for adding the items to the shopping cart, asserting the shopping
 * cart and then firing all rules.
 *
 * A reference to the JFrame is also passed so the rules can launch dialog boxes
 * for user interaction. It uses the ApplicationData feature for this.
 */
class CheckoutCallback {
  def this(kcontainer: KieContainer) {
    this()
    this.kcontainer = kcontainer
  }

  def setOutput(output: JTextArea) {
    this.output = output
  }

  /**
   * Populate the cart and assert into working memory Pass Jframe reference
   * for user interaction
   *
   * @param frame
   * @param items
   * @return cart.toString();
   */
  def checkout(frame: JFrame, items: List[Product]): String = {
    val order: Order = new Order
    import scala.collection.JavaConversions._
    for (p <- items) {
      order.addItem(new Purchase(order, p))
    }
    val ksession: KieSession = kcontainer.newKieSession("PetStoreKS")
    ksession.setGlobal("frame", frame)
    ksession.setGlobal("textArea", this.output)
    ksession.insert(new Product("Gold Fish", 5))
    ksession.insert(new Product("Fish Tank", 25))
    ksession.insert(new Product("Fish Food", 2))
    ksession.insert(new Product("Fish Food Sample", 0))
    ksession.insert(order)
    ksession.fireAllRules
    order.toString
  }

  private[petstore] var kcontainer: KieContainer = null
  private[petstore] var output: JTextArea = null
}
