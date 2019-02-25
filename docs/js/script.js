Papa.parse('formatted_output/TotalDistance.csv', {
    download: true,
    header: true,
    dynamicTyping: true,
    complete: (results) => {
        renderTable(t1, results.data, results.meta.fields)
    }
})

Papa.parse('formatted_output/TotalTrains.csv', {
    download: true,
    header: true,
    dynamicTyping: true,
    complete: (results) => {
        renderTable(t2, results.data, results.meta.fields)
    }
})

Papa.parse('formatted_output/Frequency.csv', {
    download: true,
    header: true,
    dynamicTyping: true,
    complete: (results) => {
        renderTable(t3, results.data, results.meta.fields)
    }
})

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
        renderHistogram(data)
        // renderTable(t4, results.data, results.meta.fields) // Becomes too big
    }
})

Papa.parse('formatted_output/Search.csv', {
    download: true,
    header: true,
    dynamicTyping: true,
    complete: (results) => {
        renderTable(t5, results.data, results.meta.fields)
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
                backgroundColor: 'rgba(238,77,90,0.5)'
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
    keys.push(4300)
    return keys
}

function renderTable(element, data, columns) {
    let htmlText = '<table><thead><tr>'
    for (let i of columns) {
        htmlText += `<th>${i}</th>`
    }
    htmlText += '</tr></thead><tbody>'
    for (let i of data) {
        // htmlText += '<tr><td>' + i.distance + '</td><td>' + i.distribution + '</td></tr>'
        htmlText += '<tr>'
        for (let j in i) {
            htmlText += `<td>${i[j]}</td>`
        }
        htmlText += '</tr>'
    }
    htmlText += '</tbody></table>'
    element.innerHTML = htmlText
}