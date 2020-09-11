<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">

	#container {
	  min-width: 310px;
	  max-width: 800px;
	  margin: 0 auto
	}


</style>
   
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/wordcloud.js"></script>

<script type="text/javascript">

	$(document).ready(function(){			
	
		var text = "${word}";
	
		var lines = text.split(/[,\. ]+/g),
		    data = Highcharts.reduce(lines, function (arr, word) {
		        var obj = Highcharts.find(arr, function (obj) {
		            return obj.name === word;
		        });
		        if (obj) {
		            obj.weight += 1;
		        } else {
		            obj = {
		                name: word,
		                weight: 1
		            };
		            arr.push(obj);
		        }
		        return arr;
		    }, []);

		Highcharts.chart('container', {
		    series: [{
		        type: 'wordcloud',
		        data: data,
		        name: 'Occurrences'
		    }],
		    title: {
		        text: '문화재 검색 순위 차트'
		    }
		});
		
	});
	
	
</script>

<div id="container"></div>