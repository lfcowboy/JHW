require([
 	"dojo/_base/fx",
	"dojo/_base/lang",
	"dojo/dom-style",
	"dojo/parser",
	"dojo/ready",
	"dijit/registry",
	"dojox/layout/ContentPane",
	"dojo/store/JsonRest"
], function(baseFx, lang, domStyle, parser, ready, registry, ContentPane, JsonRest){
	ready(function(){
		parser.parse().then(function(objects){
			baseFx.fadeOut({  //Get rid of the loader once parsing is done
				node: "preloader",
				onEnd: function() {
					domStyle.set("preloader","display","none");
				}
			}).play();

    		userTypesStore = new JsonRest({
    			target: "getUserTypes.do",
				headers: {"Content-Type": "application/json"}, 
				idProperty: "id"
    		});
    		
 	    	coustomerStore = new JsonRest({
     			target: "getUsersAction.do?type=UserType_6",
     			headers: {"Content-Type": "application/json"}, 
     			idProperty: "id"
     		});
 	    	
 	    	engineerStore = new JsonRest({
     			target: "getUsersAction.do?type=UserType_4",
     			headers: {"Content-Type": "application/json"}, 
     			idProperty: "id"
     		});
 	    	
 	    	productStore = new JsonRest({
     			target: "getProductsAction.do",
     			headers: {"Content-Type": "application/json"}, 
     			idProperty: "id"
     		});
		});
	});
	
	lang.setObject("jhw.basename", function(path, suffix) {
	    // Returns the filename component of the path  
	    var b = path.replace(/^.*[\/\\]/g, '');
	    return b.split(suffix)[0];
	});
	
	lang.setObject("jhw.addTab", function(tabContainer, href, title, closable){
		if (typeof tabContainer === "string"){
			tabContainer = registry.byId(tabContainer);
		}
		var tabName = "tab" + jhw.basename(href,".do"),
			tab = registry.byId(tabName);
		if (typeof tab === "undefined"){
			tab = new ContentPane({
				id: tabName,
				title: title,
				href: href,
				closable: closable,
				style: "padding: 0;"
			});
			tabContainer.addChild(tab);
		}else{
			tab.href=href;
			tab.refresh();
		}
		tabContainer.selectChild(tab);
	});
	
	lang.setObject("jhw.getTab", function(href){
		var tabName = "tab" + jhw.basename(href,".do"),
			tab = registry.byId(tabName);
		return tab;
	});
	
	lang.setObject("jhw.closeAllTab", function(tabContainer){
		if (typeof tabContainer === "string"){
			tabContainer = registry.byId(tabContainer);
		}
		var childTab = tabContainer.getChildren();
		for (var tabIndex in childTab){
			tabContainer.removeChild(childTab[tabIndex]);
			childTab[tabIndex].destroyRecursive();
		}
	});
});