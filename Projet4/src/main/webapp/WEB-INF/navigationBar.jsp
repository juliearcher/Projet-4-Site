<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item"><a class="nav-link"  href="/Projet4/">Accueil</a></li>
		<c:if test="${ not empty sessionScope.user && sessionScope.user.id > 0 }">
			<li class="nav-item"><a class="nav-link"  href="/Projet4/getHeroIncidents">Voir ses incidents</a></li>
		</c:if>
		<c:if test="${ empty sessionScope.user || sessionScope.user.id <= 0 }">
			<li class="nav-item"><a class="nav-link"  href="/Projet4/createIncident">Créer un incident</a></li>
			<li class="nav-item"><a class="nav-link"  href="/Projet4/getIncident">Assigner un héros à un incident</a></li>
		</c:if>
      </ul>
      <ul class="navbar-nav">   
		<c:if test="${ not empty sessionScope.user && sessionScope.user.id > 0 }">
			<li class="nav-item"><a class="nav-link"  href="/Projet4/logout">Se déconnecter</a></li>
		</c:if>
		<c:if test="${ empty sessionScope.user || sessionScope.user.id <= 0 }">
			<li class="nav-item"><a class="nav-link"  href="/Projet4/createHero">Créer un compte</a></li>
			<li class="nav-item"><a class="nav-link"  href="/Projet4/login">Se connecter</a></li>	
		</c:if>
      </ul>
  </div>
</nav>
