<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="/stripes.tld" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <title>My First Stripe</title>

</head>
<body>
<div class="container">
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-4">Stripes Calculator</h1>
            <p class="lead">Hi, I'm the Stripes Calculator. I can only do addition. Maybe, some day, a nice programmer
                will come along and teach me how to do other things?</p>
        </div>
    </div>
    <stripes:form action="/quickstart/Calculator.action" focus="">
    <div class="form-group row">
        <label for="numberOne" class="col-sm-2 col-form-label">Number 1</label>
        <div class="col-sm-10">
            <stripes:text name="numberOne" class="form-control"/>
        </div>
    </div>
    <div class="form-group row">
        <label for="numberTwo" class="col-sm-2 col-form-label">Number 2</label>
        <div class="col-sm-10">
            <stripes:text name="numberTwo" class="form-control"/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-2">
            <stripes:submit name="addition" value="Add" class="btn btn-primary"/>
            <stripes:submit name="division" value="Divide" class="btn btn-primary"/>
        </div>
        <div class="col-sm-8">
            <stripes:errors/>
        </div>
    </div>
    </stripes:form>
    <div class="row">
        <div class="col-sm-10">
            <p>Result: <span id="resultWrapper">${actionBean.result}</span></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-10">
            <p><a href="../index.html" class="btn btn-secondary"><< Back To Example Listing</a></p>
        </div>
    </div>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="http://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</body>
</html>