import React from "react";

import Screen from "./app/components/Screen";

import ChartInterfaceScreen from "./app/screens/ChartInterfaceScreen";



const Stock2 = 'AMD';
const Stock2Price = 135.004;
const Stock2Perc = "+5.2";



export default function App() {


  return (
    <Screen>
     
      <ChartInterfaceScreen
        STtitle={Stock2}
        STprice={Stock2Price}
        STpercentage={Stock2Perc}
        symbol={Stock2}
      />

      
    </Screen>
  );
}
