import React, {Component} from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "../fragments/Header";
import {Link} from "react-router-dom";
import ApiService from "../service/ApiService";

export default class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = {books: [], authors: [], genres: []};

        this.editBook = this.editBook.bind(this);
        this.reloadBooksList = this.reloadBooksList.bind(this);
    }


    componentDidMount() {
        this.reloadBooksList();
    }


    reloadBooksList() {
        ApiService.fetchAllBooks()
            .then(response => response.json())
            .then(books => this.setState({books}));
    }


    editBook(id) {
        window.localStorage.setItem("id", id);
        this.props.history.push('/Books/BookUpdate');
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
                            <th>act</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.books.map(book =>
                                <tr key={book.id}>
                                    <td>{book.title}</td>
                                    <td>{book.authors}</td>
                                    <td>{book.genres}</td>
                                    <td>
                                        <button className="btn btn-success" onClick={() => this.editBook(book.id)}> Edit</button>
                                    </td>
                                </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>

            </React.Fragment>
        )
    }
}
