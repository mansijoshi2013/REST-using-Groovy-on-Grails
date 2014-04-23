package grailsgumballmachinever1

import groovy.sql.Sql;
import gumballstate.GumballMachine


class GumballMachineController {

    def String machineSerialNum = "1234998871109"
    def GumballMachine gumballMachine

/*	 def sql = Sql.newInstance("jdbc:mysql://localhost:3306/cmpe281", "root",
                      "", "com.mysql.jdbc.Driver")*/
	 def sql = Sql.newInstance("jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com:3306/cmpe281lab", "mansijoshi",
		 "mansijoshi", "com.mysql.jdbc.Driver")
	 //def row = sql.firstRow('select count_gumballs from gumball')
	
	 
		 
	def updateCount(int newcount){
		
		def count = sql.execute('update gumball set count_gumballs = ?' , [newcount])
	}
	
    def getSerialNumber(){
		def snumber = sql.firstRow('select serial_number from gumball')
	}
	
	def getNumberOfGumballs(){
		def number = sql.firstRow('select count_gumballs from gumball')
	}
    def index() {
		
		String VCAP_SERVICES = System.getenv('VCAP_SERVICES')
		
        if (request.method == "GET") {
			println( "Count is : " + getNumberOfGumballs().toString())
            // search db for gumball machine
            def gumball = Gumball.findBySerialNumber( machineSerialNum )
            if ( gumball )
            {
                // create a default machine
                gumballMachine = new GumballMachine(gumball.countGumballs)
                gumballMachine.setModelNumber(gumball.modelNumber)
                gumballMachine.setSerialNumber(gumball.serialNumber)
                System.out.println(gumballMachine)
				
            }
            else
            {

				String countstr = getNumberOfGumballs().toString()
				String subStr = countstr.substring(countstr.indexOf('=')+1, countstr.length()-1)
				
				String serialstr = getSerialNumber().toString()
				String serialsubStr = serialstr.substring(serialstr.indexOf('=')+1, serialstr.length()-1)
				
				
				println(subStr)
				println(serialsubStr)
				Integer param = subStr.toInteger()
                gumballMachine = new GumballMachine(param, serialsubStr);
                System.out.println(gumballMachine)
            }

            // save in the session
            session.machine = gumballMachine
			
            // report a message to user
            flash.message = gumballMachine.toString() 

            // display view
            render(view: "index")

        }
        else if (request.method == "POST") {

            // dump out request object
            request.each { key, value ->
                println( "request: $key = $value")
            }

            // dump out params
            params?.each { key, value ->
                println( "params: $key = $value" )
            }

            // get machine from session
            gumballMachine = session.machine
            System.out.println(gumballMachine)
			
            if ( params?.event == "Insert Quarter" )
            {
                gumballMachine.insertQuarter()
            }
            if ( params?.event == "Turn Crank" )
            {	
				def before = gumballMachine.getCount() ;
                gumballMachine.turnCrank();
				def after = gumballMachine.getCount() ;
				println("AFTER VALUE  : "+after)
				updateCount(after)
				if ( after != before )
				{
					def gumball = Gumball.findBySerialNumber( machineSerialNum )
					if ( gumball )
					{
						// update gumball inventory
						gumball.countGumballs = after ;
						gumball.save();
					}
				}
				
            }

			
            // report a message to user
            flash.message = gumballMachine.toString() 

            // render view
            render(view: "index")
        }
        else {
            render(view: "/error")
        }
    }

}

