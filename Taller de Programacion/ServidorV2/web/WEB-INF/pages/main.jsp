<div id="myCarousel" class="carousel slide span10">
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
        <li data-target="#myCarousel" data-slide-to="3"></li>
    </ol>
    <!-- Carousel items -->
    <div class="carousel-inner">
        <div class="active item">
            <img src="/img/Carr/Experimenta.png" alt="Experimenta" />
            <div class="carousel-caption">
                <h4>¡Bienvenido! Experimenta Direct Market</h4>
            </div>
        </div>
        <div class="item">
            <img src="/img/Carr/Suenia.png" alt="Sueña">
            <div class="carousel-caption">
                <h4>¡Sueñalo!</h4>
            </div>
        </div>
        <div class="item">
            <img src="/img/Carr/Comparte.png" alt="Comparte" />
            <div class="carousel-caption">
                <h4>Comparte</h4>
            </div>
        </div>
        <div class="item">
            <img src="/img/Carr/Vivelo.png" alt="Vivelo" />
            <div class="carousel-caption">
                <h4>¡Vivelo!</h4>
            </div>
        </div>
    </div>
    <!-- Carousel nav -->
    <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
    <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
</div>
<script>
    $(document).ready(function() {
        $('.carousel').carousel({
            interval: 3000
        });
    });
</script>
