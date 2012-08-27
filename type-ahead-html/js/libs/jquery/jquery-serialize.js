define(['jQuery'], function(jQuery){
    jQuery.fn.serializeObject = function() {
        if (this.length < 1) {
            return false;
        }

        var $el = this;
        var data = {};
        var lookup = data; // current reference of data

        $el.find(':input[type!="checkbox"][type!="radio"], input:checked').each(
            function() {
                // data[a][b] becomes [ data, a, b ]
                var named = this.name.replace(/\[([^\]]+)?\]/g, ',$1').split(',');
                var cap = named.length - 1;

                // Ensure that only elements with valid `name` properties will
                // be serialized
                if (named[0]) {
                    for ( var i = 0; i < cap; i++) {
                        // move down the tree - create objects or array if
                        // necessary
                        lookup = lookup[named[i]] = lookup[named[i]]
                            || (named[i + 1] === "" ? [] : {});
                    }

                    // at the end, push or assign the value
                    if (lookup.length !== undefined) {
                        lookup.push(jQuery(this).val());
                    } else {
                        lookup[named[cap]] = jQuery(this).val();
                    }

                    // assign the reference back to root
                    lookup = data;
                }
            });

        return data;
    };
      
    /*
	jQuery.fn.serializeObject = function() {
		var arrayData, objectData;
		arrayData = this.serializeArray();
		objectData = {};
	
		jQuery.each(arrayData, function() {
			var value;
	
			if (this.value != null) {
				value = this.value;
			} else {
				value = '';
			}
	
			if (objectData[this.name] != null) {
				if (!objectData[this.name].push) {
					objectData[this.name] = [
						objectData[this.name]
					];
				}
	
				objectData[this.name].push(value);
			} else {
				objectData[this.name] = value;
			}
		});
	
		return objectData;
	}
	*/
});