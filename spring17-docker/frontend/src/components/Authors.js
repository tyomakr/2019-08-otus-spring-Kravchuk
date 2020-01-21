import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "../fragments/Header";
import Client from "../service/Client"


export default class Authors extends React.Component {

    constructor() {
        super();
        this.state = {authors: []};
    }

    componentDidMount() {
        Client({method: 'GET', path: '/api/v1/authors'}).done(response => {
            this.setState({authors: response.entity._embedded.authors});
        });
    }

    render() {

        return (
            <React.Fragment>
                <div className={Authors}>
                    <Helmet>
                        <meta charSet="utf-8" />
                        <title>React Spring Library - Авторы</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-2 ml-2">
                    <h2>Авторы</h2>
                </div>
                <div className="container mt-3 ml-3">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Номер</th>
                            <th>Имя автора</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.authors.map((author, i) => (
                                <tr key={i}>
                                    <td>{i}</td>
                                    <td>{author.authorName.toString()}</td>
                                </tr>
                            ))
                        }
                        </tbody>
                    </table>
                </div>
            </React.Fragment>
        )
    }
}