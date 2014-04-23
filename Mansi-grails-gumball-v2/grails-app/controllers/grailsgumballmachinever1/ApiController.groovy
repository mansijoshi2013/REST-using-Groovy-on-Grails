package grailsgumballmachinever1

import grails.converters.XML
import grails.converters.JSON
import gumballstate.GumballMachine;


class ApiController {

	def defaultAction = 'show'

	def index() {

		redirect(action: 'show')
		//render(view: "machine")
	}
	
	// HTTP GET
	def show() {

	
		if ( params.serialNumber )
		{
			def p = Gumball.findBySerialNumber(params.serialNumber)
			if ( p )
			{
				
				//render p as XML
				flash.message = p as XML
				
				// display view
				render(view: "index")
				
			}
			else {
				def all = Gumball.list()
				render all as XML
			}
		}
		else
		{
			
			println "Mansi"
			def p = Gumball.list()

			println p as XML

			flash.gumballs = p
			render(view: "machine")
			//render p as XML
		}

	}

	// HTTP PUT
	def update() {
		
/*		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}*/
		
		// print-out request
		println( "NEW Model Number: " + request.XML.modelNumber )
		println( "NEW Serial Number: " + request.XML.serialNumber )
		println( "NEW Count of Gumball: " + request.XML.countGumballs )

		if ( params.serialNumber )
		{
			println ( "Updating Serial Number: " + params.serialNumber )
			
			def p = Gumball.findBySerialNumber(params.serialNumber)
			if ( p )
			{
				if (!request.XML.modelNumber.isEmpty()) p.modelNumber = request.XML.modelNumber
				if (!request.XML.serialNumber.isEmpty()) p.serialNumber = request.XML.serialNumber
				if (!request.XML.countGumballs.isEmpty()) p.countGumballs = request.XML.countGumballs.toInteger()
				try {
					p.save(flush: true)
					render p as XML
				}
				catch (e) {
					println p.errors
					println e
					render(contentType:"text/xml")
					{
						fault {
								code('28763')
								message('Product Update Error.')
								errors {
									p.errors.allErrors.each {
										error(it)
									}
								}
						}
					}
				}

			}
		}
		else
		{
			render(contentType:"text/xml")
			{
				fault {
						code('28983')
						message('No Serial Number in Update Request.')
				}
			}
		}
				
	}

	// HTTP POST
	def save() {

		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}
		
		/* alternate way to dump request text */
		//def requestXML = request.inputStream.getText()
		//println( requestXML )
		
		/* parse request using xml slurper */
		//println( request.XML )
		
		println( request.XML.modelNumber )
		println( request.XML.serialNumber )
		println( request.XML.countGumballs )
		
		
		def p = new Gumball()
		p.id = request.XML.@id.toLong()
		p.modelNumber = request.XML.modelNumber
		p.serialNumber = request.XML.serialNumber
		p.countGumballs = request.XML.countGumballs.toInteger()
		/*def v = Vendor.findById( request.XML.vendor.@id.toLong() )
		p.vendor = v*/
		try {
			p.save(flush: true)
		}
		catch (e) {
			println p.errors
			println e
		}
	
		def saved = Gumball.findBySerialNumber(p.serialNumber)
		if ( saved )
		{
			render saved as XML
		}
		else
		{
			 auto xml
			//render p.errors as XML
			
			 custom xml
			render(contentType:"text/xml")
			{
				fault {
						code('19876')
						message('Error Creating Gumball Machine.')
						errors {
							p.errors.allErrors.each {
								error(it)
							}
						}
				}
			}
		}

	}

	// HTTP DELETE
	def delete() {
		
		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}
		
		if ( params.serialNumber )
		{
			def p = Gumball.findBySerialNumber(params.serialNumber)
			if ( p )
			{
				p.delete()
				render(contentType:"text/xml")
				{
					success {
							code('20000')
							message('Gumball Machine deleted!')
					}
				}
			}
			else {
				render(contentType:"text/xml")
				{
					fault {
							code('18876')
							message('Gumball Machine not found!')
					}
				}
			}
		}
		
	}

}



