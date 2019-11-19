import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "./Header";
import {Link} from "react-router-dom";

export default class Books extends React.Component {

    constructor() {
        super();
        this.state = {books: []};
    }

    componentDidMount() {
        fetch('/api/v1/books')
            .then(response => response.json())
            .then(books => this.setState({books}));
    }

    render() {

        return (
            <React.Fragment>
                <div className={Books}>
                    <Helmet>
                        <meta charSet="utf-8" />
                        <title>React Spring Library - Книги</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-2 ml-2">
                    <h2>Книги</h2>
                </div>


                <div className="container float-left my-sm-3">
                    <Link to={"/Books/Add"}><button className="btn btn-outline-secondary btn-sm">Новая книга</button></Link>
                </div>

                <div className="container mt-3 ml-3">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Название книги</th>
                            <th>Автор</th>
                            <th>Жанр</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.books.map((book, i) => (
                                <tr key={i}>
                                    <td><Link to={`/Books/edit/${book.id}`}>{book.title}</Link></td>

                                    <td>{book.authors}</td>
                                    <td>{book.genres}</td>
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

