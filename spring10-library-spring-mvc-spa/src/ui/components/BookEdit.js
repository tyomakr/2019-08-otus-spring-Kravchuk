import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "./Header";

export default class BookEdit extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        return (
            <React.Fragment>
                <div className={BookEdit}>
                    <Helmet>
                        <meta charSet="utf-8" />
                        <title>React Spring Library - Редактирование книги</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-2 ml-2">
                    <h2>Редактирование книги</h2>
                </div>
                <div className="container mt-3 ml-3">
                    <p>{this.props.title}</p>
                </div>
            </React.Fragment>
        )
    }
}