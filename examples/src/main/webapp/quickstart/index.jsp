<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="/stripes.tld" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">


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
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.min.js" integrity="sha384-VHvPCCyXqtD5DqJeNxl2dtTyhF78xXNXdkwX1CZeRusQfRKp+tA7hAShOK/B/fQ2" crossorigin="anonymous"></script>

</body>
</html>