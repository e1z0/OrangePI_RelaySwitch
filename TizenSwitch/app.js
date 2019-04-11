	var url = "http://192.168.254.102:1415";
    var list = document.getElementById("listRelays");
	var xhttp = new XMLHttpRequest();

( function () {
	window.addEventListener( 'tizenhwkey', function( ev ) {
		if( ev.keyName === "back" ) {
			var page = document.getElementsByClassName( 'ui-page-active' )[0],
				pageid = page ? page.id : "";
			if( pageid === "main" ) {
				try {
					tizen.application.getCurrentApplication().exit();
				} catch (ignore) {
				}
			} else {
				window.history.back();
			}
		}
	} );
	
	
loadList();


	

	
} () );

function loadList() {
	var client = new XMLHttpRequest();

	client.onreadystatechange = function() {
        if (client.readyState === 4 && client.status === 200) {
        	var data = JSON.parse(client.responseText);
        	data.forEach(function(relay) {
            if (relay.name != "...") {
        	    console.warn(relay.name);
        	    list.innerHTML+='<li id="'+relay.id+'" value="'+relay.state+'" onclick="Switchas('+relay.id+')"> <div class="ui-marquee">'+ relay.name +'</div></li>';
        	    }
        	});
        	var snapListComponent = tau.widget.SnapListview(list);
	    snapListComponent.refresh();
        }
	
};

client.open("GET", url+'/devices', true);
client.send();	


}

function Switchas(port) {
	var client = new XMLHttpRequest();
	var state = document.getElementById(port).getAttribute("value");
	var mygelis = document.getElementById(port);
	console.warn("mygelis: "+port+" state: "+state)
	if (state == 1) {
		client.open("GET",url+'/turn/'+port+'/on', true);
		console.warn('Requesting url:'+url+'/turn/'+port+'/on');
		state = 0;
	} else {
		client.open("GET",url+'/turn/'+port+'/off', true);
		console.warn('Requesting url:'+url+'/turn/'+port+'/off');
		state = 1;
	}
	mygelis.setAttribute("value",state);
	client.send();
}

