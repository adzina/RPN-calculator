package com.android.jake.rpn_calculator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import java.math.RoundingMode
import java.text.DecimalFormat

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.*
import java.util.Stack


class MainActivity : AppCompatActivity() {

    private var stack = Stack<Double>()
    private var stack_backup_1 = Stack<Double>()
    private var stack_backup_2 = Stack<Double>()
    private var stack_backup_3 = Stack<Double>()
    private var currentNumber=""
    private val nr1 = "->    "
    private val nr2 = "1: "
    private val nr3 = "2: "
    private val nr4 = "3: "
    private var line1 = ""
    private var line2 = "\n"
    private var line3 = "\n"
    private var line4 = "\n"
    private val PRECISION_REQUEST = 10000
    private var format="0.0"
    private var precision = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        readFromFile()
        printStack()
        val str = nr4 + line4 + nr3 + line3 + nr2 + line2 + nr1 + line1
        textView.setText(str)

        button0.setOnClickListener{
            currentNumber+="0"
            updateTextView()
        }
        button1.setOnClickListener{
            currentNumber+="1"
            updateTextView()
        }
        button2.setOnClickListener{
            currentNumber+="2"
            updateTextView()
        }
        button3.setOnClickListener{
            currentNumber+="3"
            updateTextView()
        }
        button4.setOnClickListener{
            currentNumber+="4"
            updateTextView()
        }
        button5.setOnClickListener{
            currentNumber+="5"
            updateTextView()
        }
        button6.setOnClickListener{
            currentNumber+="6"
            updateTextView()
        }
        button7.setOnClickListener{
            currentNumber+="7"
            updateTextView()
        }
        button8.setOnClickListener{
            currentNumber+="8"
            updateTextView()
        }
        button9.setOnClickListener{
            currentNumber+="9"
            updateTextView()
        }
        buttonEnter.setOnClickListener{

            stack.push(currentNumber.toDouble())
            currentNumber=""
            printStack()
        }
        buttonDot.setOnClickListener{
            if(!currentNumber.contains('.')){
                currentNumber+="."
                updateTextView()
            }
        }
        buttonClear.setOnClickListener{
            if(currentNumber.length>1)
                currentNumber=currentNumber.substring(0,currentNumber.length-1)
            else
                currentNumber=""

            updateTextView()
        }
        buttonPlus.setOnClickListener {
            if(stack.size>1){
                val a = stack.pop()
                val b = stack.pop()
                stack.push(a+b)
                printStack()
            }
        }
        buttonStar.setOnClickListener{
            if(stack.size>1){
                val a = stack.pop()
                val b = stack.pop()
                stack.push(a*b)
                printStack()
            }

        }
        buttonSlash.setOnClickListener{
            if(stack.size>1){
                val a = stack.pop()
                val b = stack.pop()
                stack.push(b/a)
                printStack()
            }

        }
        buttonPow.setOnClickListener{
            if(stack.size>1){

                val a = stack.pop()
                val b = stack.pop()
                stack.push(Math.pow(b, a))
                printStack()
            }

        }
        buttonMinus.setOnClickListener{
            if(stack.size>1){
                val a = stack.pop()
                val b = stack.pop()
                stack.push(b-a)
                printStack()
            }

        }
        buttonSqrt.setOnClickListener{
            if(stack.size>0){
                val a = stack.pop()
                stack.push(Math.sqrt(a))
                printStack()
            }

        }
        buttonSwap.setOnClickListener{
            var a = stack.pop()
            var b= stack.pop()
            stack.push(a)
            stack.push(b)
            printStack()
        }
        buttonDrop.setOnClickListener{
            if(!stack.isEmpty())
                stack.pop()
            if(!stack_backup_1.isEmpty())
                stack_backup_1.pop()
            if(!stack_backup_2.isEmpty())
                stack_backup_2.pop()
            if(!stack_backup_3.isEmpty())
                stack_backup_3.pop()
            printStack()
        }
        buttonAllClear.setOnClickListener{
            stack.clear()
            stack_backup_3.clear()
            stack_backup_2.clear()
            stack_backup_1.clear()
            printStack()
        }
        buttonPlusMinus.setOnClickListener{
            if(!currentNumber.isEmpty()){
                if(currentNumber[0]!='-'){
                    currentNumber = '-'+currentNumber
                }
                else{
                    currentNumber = currentNumber.substring(1)
                }
            }

            updateTextView()
        }
        buttonUndo.setOnClickListener{

            Log.i("info", stack.toString())
            Log.i("info", stack_backup_1.toString())
            Log.i("info", stack_backup_2.toString())
            Log.i("info", stack_backup_3.toString())

            Log.i("info","------------------------")
            stack.clear()

            copyStack(stack_backup_1,stack)

            stack_backup_1.clear()
            copyStack(stack_backup_2,stack_backup_1)
            stack_backup_2.clear()
            copyStack(stack_backup_3,stack_backup_2)
            stack_backup_3.clear()

            Log.i("info", stack.toString())
            Log.i("info", stack_backup_1.toString())
            Log.i("info", stack_backup_2.toString())
            Log.i("info", stack_backup_3.toString())

            line1 = "\n"
            line2 = "\n"
            line3 = "\n"
            line4 = "\n"

            val form = DecimalFormat(format)

            if(stack.size>0){ //na stosie przynajmniej 1 liczba
                var val2 = stack.pop()
                line2 = form.format(val2)+"\n"
                if(stack.size>0){ //na stosie przynajmniej 2 liczby
                    var val3 = stack.pop()
                    line3 = form.format(val3)+"\n"

                    if(stack.size>0){ // na stosie przynajmniej 3 liczby
                        var val4 = stack.pop()
                        line4 = form.format(val4)+"\n"
                        if(stack.size>0){
                            var val5 = stack.pop()
                            stack.push(val5)
                        }
                        stack.push(val4)
                    }
                    stack.push(val3)
                }
                stack.push(val2)

            }


            val to_display = nr4 + line4 + nr3 + line3 + nr2 + line2 + nr1 + line1
            textView.setText(to_display)
        }
    }
    private fun copyStack(stack1:Stack<Double>,stack2:Stack<Double>){
        var stmp = Stack<Double>()
        reverseStack(stack1,stmp)
        reverseStack(stmp,stack2)
    }
    private fun reverseStack(stack1:Stack<Double>,stack2:Stack<Double>):Stack<Double>{
        while (!stack1.isEmpty()){
            stack2.push(stack1.pop())
                }
        return stack2
    }
    private fun createFormat(){
        var i=0
        format = "0."
        while(i<precision){
            format+="0"
            i+=1
        }
    }

    override fun onDestroy() {
        var content:String = ""
        while(!stack.isEmpty()){
            content+=stack.pop().toString()+"\n"
        }

        try {
            val file = OutputStreamWriter(openFileOutput("stack.txt", Activity.MODE_PRIVATE))

            file.write (content)
            file.flush ()
            file.close ()
        } catch (e : IOException) {
        }

        try {
            val file = OutputStreamWriter(openFileOutput("precision.txt", Activity.MODE_PRIVATE))

            file.write (precision.toString())
            file.flush ()
            file.close ()
        } catch (e : IOException) {
        }
        super.onDestroy()




    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val i = Intent(this, SettingsActivity::class.java)
        i.putExtra("Parametr","Twoje dane")
        startActivityForResult(i, PRECISION_REQUEST)

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if((requestCode==PRECISION_REQUEST)
                && (resultCode== Activity.RESULT_OK)){
            if(data!=null){
                if(data.hasExtra("returnString")){
                    var d = data.extras.getString("returnString")
                    precision = d.toInt()
                    createFormat()
                }
            }
        }
    }

    private fun readFromFile(){

        if(fileList().contains("stack.txt")) {
            var stmp = Stack<Double>()
            try {
                val file = InputStreamReader(openFileInput("stack.txt"))
                val br = BufferedReader(file)
                var line = br.readLine()
                while (line != null) {
                    stmp.push(line.toDouble())
                    line = br.readLine()
                }
                br.close()
                file.close()
                reverseStack(stmp, stack)
            }
            catch (e:IOException) {
            }
          if(fileList().contains("precision.txt")){
              try {
                  val file = InputStreamReader(openFileInput("precision.txt"))
                  val br = BufferedReader(file)
                  var line = br.readLine()
                  while (line != null) {
                      precision = line.toInt()
                      line = br.readLine()
                  }
                  createFormat()
                  br.close()
                  file.close()
              }
              catch (e:IOException) {
              }
          }
        }

    }
    private fun printStack(){
        line1 = "\n"
        line2 = "\n"
        line3 = "\n"
        line4 = "\n"

        val form = DecimalFormat(format)

        if(stack.size>0){ //na stosie przynajmniej 1 liczba
            var val2 = stack.pop()
            line2 = form.format(val2)+"\n"
            if(stack.size>0){ //na stosie przynajmniej 2 liczby
                var val3 = stack.pop()
                line3 = form.format(val3)+"\n"

                if(stack.size>0){ // na stosie przynajmniej 3 liczby
                    var val4 = stack.pop()
                    line4 = form.format(val4)+"\n"
                    if(stack.size>0){
                        var val5 = stack.pop()
                        stack_backup_3.push(val5)
                        stack.push(val5)
                    }
                    stack.push(val4)
                    stack_backup_2.push(val4)
                }
                stack.push(val3)
                stack_backup_1.push(val3)
            }
            stack.push(val2)

        }


        val str = nr4 + line4 + nr3 + line3 + nr2 + line2 + nr1 + line1
        textView.setText(str)

    }
    private fun updateTextView(){

        val str = nr4 + line4 + nr3 + line3 + nr2 + line2 + nr1 + currentNumber+"\n"
        line1 = currentNumber+"\n"
        textView.setText(str)
    }

}


