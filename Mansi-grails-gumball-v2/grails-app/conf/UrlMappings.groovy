class UrlMappings {

	static mappings = {
		
		
		/*
		 "/product/$sku"(controller: "api", parseRequest: true) {
			 action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		 }
		 */
 
		 "/machine"(controller: "api", parseRequest: true) {
			 action = [GET: "show", POST: "save"]
		 }
		 "/machine/$serialNumber"(controller: "api", parseRequest: true) {
			 action = [GET: "show", PUT: "update", DELETE: "delete"]
		 }
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		//"/machine"(view:"gumballMachine/machine")
		"500"(view:'/error')
	}
}
