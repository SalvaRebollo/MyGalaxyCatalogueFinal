$(document).ready(function() { // Se ejecuta en cuanto la página está lista
    $.ajax({
        url: "https://my-galaxy-catalogue-php.herokuapp.com/index.php?mod=statistics&ope=getProductStatistics",
        type: 'post',
        headers: {
            "Access-Control-Allow-Origin": "*"  //for object property name, use quoted notation shown in second
        },
        success: function(data) {
            var newData = JSON.parse(data);


            newData.forEach(function(s) {
                var newDate = s.Fecha.split("-");
                s.Fecha = newDate[2] + "/" + newDate[1] + "/" + newDate[0];
                s.Cantidad = parseInt(s.Cantidad);
            });

            console.log(newData);

            var datos = { labels: [], cantidad: [] };

            if (newData.length < 9) {
                for (var i = newData.length-1; i >= 0 ; i--) {
                    datos.labels.push(newData[i].Fecha);
                    datos.cantidad.push(newData[i].Cantidad);
                }

            } else {

                for (var i = 9; i >= 0; i--) {
                    datos.labels.push(newData[i].Fecha);
                    datos.cantidad.push(newData[i].Cantidad);
                }
            }

            // Bar chart
            new Chart(document.getElementById("bar-chart"), {
                type: 'bar',
                data: {
                    labels: datos.labels,
                    datasets: [
                        {
                            label: "Productos (unidades)",
                            backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850", "#1ba39c","#00aa55","#b381b3","#aa8f00","#ff4500"],
                            data: datos.cantidad
                        }
                    ]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true,
                                stepSize: 1,
                                fontColor: '#FFFFFF',
                                fontSize: 15
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                fontColor: '#FFFFFF',
                                fontSize: 15
                            }
                        }]
                    },
                    legend: { display: false },
                    title: {
                        display: false,
                        text: 'Productos registrados en los últimos 10 días',
                        fontColor: '#FFFFFF',
                        fontSize: 20
                    }
                }
            });





        }
    });
});

