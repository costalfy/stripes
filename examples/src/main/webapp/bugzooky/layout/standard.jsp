    <%@ include file="/bugzooky/taglibs.jsp" %>

        <stripes:layout-definition>
            <!doctype html>
            <html lang="en">
            <head>
            <!-- Required meta tags -->
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

            <!-- Bootstrap CSS -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
            integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

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
            <script src="http://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
            <script type="text/javascript" src="${ctx}/bugzooky/bugzooky.js"></script>
            </body>
            </html>
        </stripes:layout-definition>
