    <%@ include file="/bugzooky/taglibs.jsp" %>

        <stripes:layout-definition>
            <!doctype html>
            <html lang="en">
            <head>
            <!-- Required meta tags -->
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

            <!-- Bootstrap CSS -->
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">


                <title>Bugzooky - ${title}</title>
            <link rel="stylesheet" type="text/css" href="${ctx}/bugzooky/bugzooky.css"/>

            <stripes:layout-component name="html-head"/>
            </head>
            <body>
            <div id="contentPanel">
            <stripes:layout-component name="header">
                <jsp:include page="/bugzooky/layout/header.jsp"/>
            </stripes:layout-component>

            <div id="pageContent">
            <div class="sectionTitle">${title}</div>
            <stripes:messages/>
            <stripes:layout-component name="contents"/>
            </div>

            <div id="footer">
            <stripes:url var="view" beanclass="net.sourceforge.stripes.examples.bugzooky.ViewResourceActionBean"/>
            <stripes:link href="${view}${pageContext.request.servletPath}">
                View this JSP
            </stripes:link>

            | View other source files:
            <stripes:useActionBean beanclass="net.sourceforge.stripes.examples.bugzooky.ViewResourceActionBean"
                                   var="bean"/>
            <select style="width: 350px;" onchange="document.location = this.value;">
            <c:forEach items="${bean.availableResources}" var="file">
                <option value="${view}${file}">${file}</option>
            </c:forEach>
            </select>
            | Built on <a href="https://github.com/StripesFramework/stripes">Stripes</a>
            </div>
            </div>
            <!-- Optional JavaScript -->
            <!-- jQuery first, then Popper.js, then Bootstrap JS -->
            <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.min.js" integrity="sha384-VHvPCCyXqtD5DqJeNxl2dtTyhF78xXNXdkwX1CZeRusQfRKp+tA7hAShOK/B/fQ2" crossorigin="anonymous"></script>

            <script type="text/javascript" src="${ctx}/bugzooky/bugzooky.js"></script>
            </body>
            </html>
        </stripes:layout-definition>
