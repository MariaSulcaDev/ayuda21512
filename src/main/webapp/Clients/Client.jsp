<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <title>Clientes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <style>
        * {
            box-sizing: border-box;
            padding: 0;
            margin: 0;
            font-size: 13px;
        }

        .container {
            max-width: 2000px;
        }

        table {
            width: 100%;
            table-layout: auto;
        }

        thead th, tbody td {
            text-align: center;
            vertical-align: middle;
        }

        .header {
            background-color: #007bff;
            color: white;
            padding: 15px;
            text-align: center;
            margin-bottom: 20px;
        }

        .modal-header {
            background-color: #f8f9fa;
        }

        .modal-body {
            padding: 20px;
        }

        .btn-primary {
            background-color: #0056b3;
        }
    </style>
    <script>
        function toggleCompanyFields() {
            var clientType = document.getElementById("client_type").value;
            var companyFields = document.getElementById("company_fields");
            companyFields.style.display = clientType === 'E' ? "block" : "none";
        }

        function setDocumentLength() {
            var documentType = document.getElementById("document_type").value;
            var documentNumber = document.getElementById("document_number");
            if (documentType === 'DNI') {
                documentNumber.maxLength = 8;
                documentNumber.placeholder = "Max 8 digits";
            } else if (documentType === 'CNE') {
                documentNumber.maxLength = 20;
                documentNumber.placeholder = "Max 20 digits";
            }
        }

        function validatePhone() {
            var phone = document.getElementById("phone");
            if (phone.value.length > 9) {
                alert("El número de teléfono no puede exceder los 9 dígitos");
                phone.value = phone.value.slice(0, 9);
            }
        }
    </script>
</head>

<body>
<div class="container">
    <div class="header">
        <h1>Listado de Clientes</h1>
    </div>
    <div class="card pb-4">
        <div class="card-header">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                    data-bs-target="#addClientModal">
                Añadir Cliente
            </button>


        </div>
        <table class="table table-bordered">
            <thead class="table-success text-center">
            <tr>
                <th>Id</th>
                <th>Tipo de Cliente</th>
                <th>Nombres</th>
                <th>Tipo de Documento</th>
                <th>Número de Documento</th>
                <th>Teléfono</th>
                <th>Correo</th>
                <th>Dirección</th>
                <th>Nombre Empresa</th>
                <th>Contacto Empresa</th>
                <th>Fecha de Registro</th>
                <th>Acciones</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="cliente" items="${client}">
                <tr>
                    <td>${cliente.client_id}</td>
                    <td>
                        <c:choose>
                            <c:when test="${cliente.client_type == 'P'}">Persona</c:when>
                            <c:when test="${cliente.client_type == 'E'}">Empresa</c:when>
                            <c:otherwise>Desconocido</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${cliente.full_name}</td>
                    <td class="text-center">${cliente.document_type}</td>
                    <td class="text-center">${cliente.document_number}</td>
                    <td class="text-center">${cliente.phone}</td>
                    <td class="text-center">${cliente.email}</td>
                    <td>${cliente.address}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty cliente.company_name}">${cliente.company_name}</c:when>
                            <c:otherwise>--</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty cliente.company_contact}">${cliente.company_contact}</c:when>
                            <c:otherwise>--</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-center">
                        <fmt:formatDate value="${cliente.registration_date}" pattern="dd-MMM-yyyy"/>
                    </td>

                    <td class="p-2">
                        <button class="btn btn-success mb-3"
                                onclick="openEditModal(${cliente.client_id}, '${cliente.client_type}', '${cliente.full_name}', '${cliente.document_type}', '${cliente.document_number}', '${cliente.phone}', '${cliente.email}', '${cliente.address}', '${cliente.company_name}', '${cliente.company_contact}', '${cliente.registration_date}')">
                            Editar
                        </button>
                        <form action="/eliminarCliente" method="post" style="display:inline;">
                            <input type="hidden" name="client_id" value="${cliente.client_id}">
                            <button type="submit" class="btn btn-danger"
                                    onclick="return confirm('¿Estás seguro de que deseas eliminar este cliente?');">
                                Eliminar
                            </button>
                        </form>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- Modal para agregar cliente -->
    <div class="modal fade" id="addClientModal" tabindex="-1"
         aria-labelledby="addClientModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg"> <!-- Aumentar el tamaño del modal -->
            <div class="modal-content">
                <!-- Modal Header -->
                <div class="modal-header">
                    <h5 class="modal-title" id="addClientModalLabel">Agregar Nuevo Cliente
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>

                <!-- Modal Body -->
                <div class="modal-body">
                    <form action="/crearCliente" method="post">
                        <div class="mb-3">
                            <label for="client_type" class="form-label">Tipo de
                                Cliente</label>
                            <select class="form-select" name="client_type"
                                    id="client_type" onchange="toggleCompanyFields()"
                                    required>
                                <option value="P">Persona</option>
                                <option value="E">Empresa</option>
                            </select>
                        </div>

                        <!-- Fieldset para Persona -->
                        <fieldset id="personal_fields">
                            <legend>Información de Persona</legend>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="full_name"
                                           class="form-label">Nombres</label>
                                    <input type="text" class="form-control"
                                           name="full_name" id="full_name" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="document_type" class="form-label">Tipo
                                        de Documento</label>
                                    <select class="form-select" name="document_type"
                                            id="document_type"
                                            onchange="setDocumentLength()" required>
                                        <option value="DNI">DNI</option>
                                        <option value="CNE">CNE</option>
                                        <option value="RUC">RUC</option>
                                    </select>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="document_number"
                                           class="form-label">Número de
                                        Documento</label>
                                    <input type="text" class="form-control"
                                           name="document_number" id="document_number"
                                           maxlength="8" placeholder="Max 8 digits"
                                           required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="phone"
                                           class="form-label">Teléfono</label>
                                    <input type="text" class="form-control"
                                           name="phone" id="phone" maxlength="9"
                                           oninput="validatePhone()" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="email"
                                           class="form-label">Correo</label>
                                    <input type="email" class="form-control"
                                           name="email" id="email" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="address"
                                           class="form-label">Dirección</label>
                                    <input type="text" class="form-control"
                                           name="address" id="address" required>
                                </div>
                            </div>
                        </fieldset>

                        <!-- Fieldset para Empresa -->
                        <fieldset id="company_fields" style="display: none;">
                            <legend>Información de Empresa</legend>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="company_name"
                                           class="form-label">Nombre Empresa</label>
                                    <input type="text" class="form-control"
                                           name="company_name" id="company_name">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="company_contact"
                                           class="form-label">Contacto Empresa</label>
                                    <input type="text" class="form-control"
                                           name="company_contact" id="company_contact">
                                </div>
                            </div>
                        </fieldset>

                        <div class="mb-3">
                            <label for="registration_date" class="form-label">Fecha de
                                Registro</label>
                            <input type="date" class="form-control"
                                   name="registration_date" id="registration_date"
                                   required>
                        </div>

                        <div class="modal-footer pt-4">
                            <button type="submit"
                                    class="btn btn-primary">Insertar
                            </button>
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Cerrar
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal para editar cliente -->
    <div class="modal fade" id="editClientModal" tabindex="-1" aria-labelledby="editClientModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editClientModalLabel">Editar Cliente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editClientForm" action="/actualizarCliente" method="post">
                        <input type="hidden" name="client_id" id="edit_client_id">

                        <div class="mb-3">
                            <label for="edit_client_type" class="form-label">Tipo de Cliente</label>
                            <select class="form-select" name="client_type" id="edit_client_type"
                                    onchange="toggleCompanyFields()">
                                <option value="P">Persona</option>
                                <option value="E">Empresa</option>
                            </select>
                        </div>

                        <!-- Fieldset para Persona -->
                        <fieldset id="personal_fields">
                            <legend>Información de Persona</legend>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="edit_full_name" class="form-label">Nombres</label>
                                    <input type="text" class="form-control" name="full_name" id="edit_full_name"
                                           required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="edit_document_type" class="form-label">Tipo de Documento</label>
                                    <select class="form-select" name="document_type" id="edit_document_type"
                                            onchange="setDocumentLength()">
                                        <option value="DNI">DNI</option>
                                        <option value="CNE">CNE</option>
                                        <option value="RUC">RUC</option>
                                    </select>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="edit_document_number" class="form-label">Número de Documento</label>
                                    <input type="text" class="form-control" name="document_number"
                                           id="edit_document_number" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="edit_phone" class="form-label">Teléfono</label>
                                    <input type="text" class="form-control" name="phone" id="edit_phone" maxlength="9"
                                           oninput="validatePhone()" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="edit_email" class="form-label">Correo</label>
                                    <input type="email" class="form-control" name="email" id="edit_email" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="edit_address" class="form-label">Dirección</label>
                                    <input type="text" class="form-control" name="address" id="edit_address" required>
                                </div>
                            </div>
                        </fieldset>

                        <!-- Fieldset para Empresa -->
                        <fieldset id="edit_company_fields" style="display: none;">
                            <legend>Información de Empresa</legend>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="edit_company_name" class="form-label">Nombre Empresa</label>
                                    <input type="text" class="form-control" name="company_name" id="edit_company_name">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="edit_company_contact" class="form-label">Contacto Empresa</label>
                                    <input type="text" class="form-control" name="company_contact"
                                           id="edit_company_contact">
                                </div>
                            </div>
                        </fieldset>

                        <div class="mb-3">
                            <label for="edit_registration_date" class="form-label">Fecha de Registro</label>
                            <input type="date" class="form-control" name="registration_date" id="edit_registration_date"
                                   required>
                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Actualizar</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
<script>
    function openEditModal(clientId, clientType, fullName, documentType, documentNumber, phone, email, address, companyName, companyContact, registrationDate) {
        document.getElementById("edit_client_id").value = clientId;
        document.getElementById("edit_client_type").value = clientType;
        document.getElementById("edit_full_name").value = fullName;
        document.getElementById("edit_document_type").value = documentType;
        document.getElementById("edit_document_number").value = documentNumber;
        document.getElementById("edit_phone").value = phone;
        document.getElementById("edit_email").value = email;
        document.getElementById("edit_address").value = address;
        document.getElementById("edit_company_name").value = companyName || '';
        document.getElementById("edit_company_contact").value = companyContact || '';
        document.getElementById("edit_registration_date").value = registrationDate.split('T')[0];

        if (clientType === 'E') {
            document.getElementById("edit_company_fields").style.display = "block";
        } else {
            document.getElementById("edit_company_fields").style.display = "none";
        }

        var editModal = new bootstrap.Modal(document.getElementById('editClientModal'));
        editModal.show();
    }

</script>

</body>

</html>
