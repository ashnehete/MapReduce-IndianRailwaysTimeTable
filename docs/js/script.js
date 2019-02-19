Papa.parse('formatted_output/DistanceDistribution.csv', {
    download: true,
    header:true,
    complete: (results) => {
        console.log(results)
    }
})