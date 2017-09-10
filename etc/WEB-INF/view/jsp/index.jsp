<!DOCTYPE html>
<html>
  <head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  <!-- inject:css -->
  <link rel="stylesheet" href="resources/css/font-awesome.min.css">
  <link rel="stylesheet" href="resources/css/jquery.orgchart.css">
  <link rel="stylesheet" href="resources/css/style.css">
  
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <script type="text/javascript" src="resources/js/jquery.mockjax.min.js"></script>
  <script  type="text/javascript"  src="resources/js/jquery.orgchart.js"></script>
    
</head>
  <body>
    <div id="chart-container"></div>
    
   <script>
      jQuery(document).ready(function() {
          $.get("/orgtree/display", function( data ) {
              $('#chart-container').orgchart({
                    'data' : data,
                    'depth': 6,
                    'nodeContent': 'title',
                  });
            });
      });
    </script>
  </body>
</html>