import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { SplashInteractor } from "KaMPStarter";

function App() {
  SplashInteractor();

  return (
    <div className="App">
      <header className="App-header">
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
      </header>
    </div>
  );
}

export default App;
