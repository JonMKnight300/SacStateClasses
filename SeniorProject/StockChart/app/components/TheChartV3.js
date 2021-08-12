import React from 'react';

import { LineChart } from 'react-native-svg-charts'


function TheChartV3({ data }) {



    return (
        <LineChart
        style={{ height: 200 }}
        data={data}
        svg={{ stroke: 'rgb(134, 65, 244)' }}
        contentInset={{ top: 20, bottom: 20 }}
    >
      
    </LineChart>
    );
}

export default TheChartV3;