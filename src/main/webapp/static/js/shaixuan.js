$('.clearDd').show();



var okSelect = []; //已经选择好的
var oSelectList = document.getElementById('selectList');

var oClearList = $(".hasBeenSelected .clearList");
var oCustext1 = document.getElementById('custext1');
var oCustext2 = document.getElementById('custext2');
var aItemTxt = oSelectList.getElementsByTagName('a');


oSelectList.onclick = function(e) {
    var ev = e || window.event;
    var tag = ev.target || ev.srcElement;
    if(!tag)return;
    var tagName = tag.nodeName.toUpperCase();
    var infor = '';
    var radioVal = '';
    var aRadio = document.getElementsByName('radio2');
    

    if (tagName == 'INPUT'&&tag.type!='radio') {
        var val = next(tag);
        if (tag.checked) {
            var sType = prev(parents(tag, 'dd')).innerHTML;
            val && okSelect.push(trim(val.innerHTML) + '|' + sType)
        } else {
            var sType = prev(parents(tag, 'dd')).innerHTML;
            delStr(val.innerHTML + '|' + sType, okSelect)
        }
    }

    if (tagName == 'A') { //如果点击 的是 A
        var oPrevInput = prev(tag);

        if (!oPrevInput) { //如果上一个节点没有则认为点击的是 '不限'
            var parent = parents(tag, 'dd');
            var aItem = parent.getElementsByTagName('label');
            var sType = '';
            for (var i = 0, len = aItem.length; i < len; i++) {
                sType = prev(parents(aItem[i], 'dd')).innerHTML;
                delStr(text(aItem[i]) + '|' + sType, okSelect);
                aItem[i].children[0].checked = false;
            }

        } else {

            if (oPrevInput && oPrevInput.getAttribute('type').toUpperCase() == 'RADIO') { //radio
                oPrevInput.checked = true;
            }

            if (oPrevInput && oPrevInput.getAttribute('type').toUpperCase() == 'CHECKBOX') { //获取checkbox
                if (oPrevInput.checked) {
                    oPrevInput.checked = false;
                    var sType = prev(parents(tag, 'dd')).innerHTML;
                    delStr(tag.innerHTML + '|' + sType, okSelect);
                } else {
                    oPrevInput.checked = true;
                    var sType = prev(parents(tag, 'dd')).innerHTML;
                    okSelect.push(trim(tag.innerHTML) + '|' + sType);
                }
            }
        }
    };

    for (var i = 0; i < aRadio.length; i++) {
        if (aRadio[i].checked) {
            radioVal = next(aRadio[i]).innerHTML;
            break;
        }
    }

    if (radioVal) infor += '<div class=\"selectedInfor selectedShow\"><span>年龄段</span><label>' + radioVal + '</label><em p="2"></em></div>';

    var vals = [];
    for (var i = 0,
    size = okSelect.length; i < size; i++) {
        vals = okSelect[i].split('|');
        infor += '<div class=\"selectedInfor selectedShow\"><span>' + vals[1] + '</span><label>' + vals[0] + '</label><em></em></div>';
    }
    
    var A_city=document.getElementsByName("A_city");
    var A_age=document.getElementsByName("radio2");
    var A_type=document.getElementsByName("A_type");
    var city="";
    var age="";
    var type="";
    for(var i=0;i<A_city.length;i++){
    	if(A_city[i].checked==true){
    		city= city+A_city[i].value+',';
    	}   	
    }
    if(city!=""){city=city.substring(0, city.length-1);}
    for(var i=0;i<A_age.length;i++){
    	if(A_age[i].checked==true){
    		age=A_age[i].value;
    		break;
    	}   	
    }
    for(var i=0;i<A_type.length;i++){
    	if(A_type[i].checked==true){
    		type= type+A_type[i].value+',';
    	}   	
    }
    if(type!=""){type=type.substring(0, type.length-1);}
    var urlString="/backend/activity/list?";
    if(city!=""){urlString=urlString+"city="+city+"&&";}
    if(age!=""){urlString=urlString+"age="+age+"&&";}
    if(type!=""){urlString=urlString+"type="+type+"&&";}
    if(city!=""||age!=""||type!=""){
        urlString=urlString.substring(0, urlString.length-2);
    }
    else{
    	urlString=urlString.substring(0, urlString.length-1);
    }
    location.href=urlString;
    
    /*oClearList.html(infor);*/
    
};
$('div.eliminateCriteria').click(function(){
    $(oSelectList).find('input').attr('checked',false);
    radioVal = '';
    okSelect.length = 0;
    $(oSelectList).trigger('click', 1);
})

$('.clearList').find('em').live('click',function(){
    var self = $(this);
    var val = self.prev().html() + '|' + self.prev().prev().html();
    var n = -1;
    var a = this.getAttribute('p') || 1;
    self.die('click');
    var html = val.split('|')[0];
    for(var i = 0, len = aItemTxt.length; i < len; i++) {
         if(trim(aItemTxt[i].innerHTML) == html) {
              prev(aItemTxt[i]).checked = false;
              break;
        }
    };
    delStr(val, okSelect);
    $(oSelectList).trigger('click', a);

})

function Selected(tag) {
	var oPrevInput = prev(tag);
	var infor = '';
	oPrevInput.checked = true;
	var sType = prev(parents(tag, 'dd')).innerHTML;
	okSelect.push(trim(tag.innerHTML) + '|' + sType);
	var vals = [];
	for (var i = 0,	size = okSelect.length; i < size; i++) {
	    vals = okSelect[i].split('|');
	    infor += '<div class=\"selectedInfor selectedShow\"><span>' + vals[1] + '</span><label>' + vals[0] + '</label><em></em></div>';
	}
	oClearList.html(infor);
}

function delStr(str, arr) { //删除数组给定相同的字符串
    var n = -1;
    for (var i = 0,
    len = arr.length; i < len; i++) {
        if (str == arr[i]) {
            n = i;
            break;
        }
    }
    n > -1 && arr.splice(n, 1);
};
function trim(str) {
    return str.replace(/^\s+|\s+$/g, '')
};
function text(e) {
    var t = '';
    e = e.childNodes || e;
    for (var j = 0; j < e.length; j++) {
        t += e[j].nodeType != 1 ? e[j].nodeValue: text(e[j].childNodes);
    }
    return trim(t);
}

function prev(elem) {
    do {
        elem = elem.previousSibling;
    } while ( elem && elem . nodeType != 1 );
    return elem;
};

function next(elem) {
    do {
        elem = elem.nextSibling;
    } while ( elem && elem . nodeType != 1 );
    return elem;
}

function parents(elem, parents) {  //查找当前祖先辈元素需要的节点  如 parents(oDiv, 'dd') 查找 oDiv 的祖先元素为dd 的
    if(!elem || !parents) return;
    var parents = parents.toUpperCase();
    do{
        elem = elem.parentNode;
    } while( elem.nodeName.toUpperCase() != parents );
    return elem;
};
