import React from 'react';
import './css/style.css';
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "./fragments/Header";


export default class App extends React.Component {

    render() {

        return (
            <React.Fragment>
                <div className={App}>
                    <Helmet>
                        <meta charSet="utf-8"/>
                        <title>React Spring Library - Главная страница</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-2 ml-2">
                    <span><b>Современные приложения на Spring WebFlux</b></span>
                    <ul>
                        <li>Вместо классического потока и embedded Web-сервера использовать WebFlux.</li>
                    </ul>
                </div>
            </React.Fragment>
        )
    }
}
