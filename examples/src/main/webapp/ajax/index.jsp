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


    <title>My First Ajax Stripe</title>

</head>
<body>
<h1>Stripes Ajax Calculator</h1>

<p>Hi, I'm the Stripes Calculator. I can only do addition. Maybe, some day, a nice programmer
    will come along and teach me how to do other things?</p>

<stripes:form action="/Calculator.action">
    <table>
        <tr>
            <td>Number 1:</td>
            <td><stripes:text name="numberOne"/></td>
        </tr>
        <tr>
            <td>Number 2:</td>
            <td><stripes:text name="numberTwo"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <stripes:submit name="add" value="Add"
                                onclick="invoke(this.form, this.name, 'resultWrapper');"/>
                <stripes:submit name="divide" value="Divide"
                                onclick="invoke(this.form, this.name, 'resultWrapper');"/>
            </td>
        </tr>
        <tr>
            <td>Result:</td>
            <td id="resultWrapper"></td>
        </tr>
    </table>
</stripes:form>
<p><a href="../index.html"><< Back To Example Listing</a></p>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.min.js" integrity="sha384-VHvPCCyXqtD5DqJeNxl2dtTyhF78xXNXdkwX1CZeRusQfRKp+tA7hAShOK/B/fQ2" crossorigin="anonymous"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/ajax/prototype.js"></script>
<script type="text/javascript" xml:space="preserve">
            /*
             * Function that uses Prototype to invoke an action of a form. Slurps the values
             * from the form using prototype's 'Form.serialize()' method, and then submits
             * them to the server using prototype's 'Ajax.Updater' which transmits the request
             * and then renders the resposne text into the named container.
             *
             * @param form reference to the form object being submitted
             * @param event the name of the event to be triggered, or null
             * @param container the name of the HTML container to insert the result into
             */
            function invoke(form, event, container) {
                if (!form.onsubmit) {
                    form.onsubmit = function () {
                        return false
                    }
                }
                ;
                var params = Form.serialize(form, {submit: event});
                new Ajax.Updater(container, form.action, {method: 'post', parameters: params});
            }
        </script>
</body>
</html>