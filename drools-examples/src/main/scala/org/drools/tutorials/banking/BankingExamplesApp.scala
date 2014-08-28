package org.drools.tutorials.banking

import java.awt.event.ActionEvent
import java.awt.{GridLayout, Container}
import javax.swing._

/**
 * Created by Simon on 2014/8/28
 */
object BankingExamplesApp extends JFrame("JBoss BRMS Banking tutorial") with App{

  setContentPane(createContentPane)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  pack()
  setVisible(true)


  private def createContentPane: Container = {
    val contentPane: JPanel = new JPanel(new GridLayout(0, 1))
    contentPane.add(new JLabel("Which tutorial do you want to see in the output?"))
    contentPane.add(new JButton(new AbstractAction("BankingExample1") {
      def actionPerformed(e: ActionEvent) {
        BankingExample1.main(new Array[String](0))
      }
    }))
    contentPane.add(new JButton(new AbstractAction("BankingExample2") {
      def actionPerformed(e: ActionEvent) {
        BankingExample2.main(new Array[String](0))
      }
    }))
    contentPane.add(new JButton(new AbstractAction("BankingExample3") {
      def actionPerformed(e: ActionEvent) {
        BankingExample3.main(new Array[String](0))
      }
    }))
    contentPane.add(new JButton(new AbstractAction("BankingExample4") {
      def actionPerformed(e: ActionEvent) {
        BankingExample4.main(new Array[String](0))
      }
    }))
    contentPane.add(new JButton(new AbstractAction("BankingExample5") {
      def actionPerformed(e: ActionEvent) {
        BankingExample5.main(new Array[String](0))
      }
    }))
    contentPane.add(new JButton(new AbstractAction("BankingExample6") {
      def actionPerformed(e: ActionEvent) {
        BankingExample6.main(new Array[String](0))
      }
    }))
    contentPane
  }

}
