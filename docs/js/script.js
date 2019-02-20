Papa.parse('formatted_output/DistanceDistribution.csv', {
    download: true,
    header: true,
    dynamicTyping: true,
    complete: (results) => {
        let data = [];
        for (let i of results.data) {
            const bin = Math.floor(i.distance / 100);
            if (!data[bin]) data[bin] = 0;
            data[bin] = data[bin] + 1;
        }
        console.log(data)
        renderHistogram(data)
    }
})

function renderHistogram(data) {
    let ctx = distribution.getContext('2d')
    let chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: keys(data),
            datasets: [{
                label: 'Frequency',
                data: data,
                backgroundColor: 'rgba(238,77,90,0.5)',
            }]
        },
        options: {
            scales: {
                xAxes: [{
                    display: false,
                    barPercentage: 1.3,
                    ticks: {
                        max: 3,
                    }
                }, {
                    display: true,
                    ticks: {
                        autoSkip: false,
                        max: 4,
                    }
                }],
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}

function keys(data) {
    let keys = Object.keys(data)
    for (let i in keys) {
        keys[i] = parseInt(keys[i]) * 100
    }
    console.log(keys)
    return keys
}